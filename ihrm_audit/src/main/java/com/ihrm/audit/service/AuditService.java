package com.ihrm.audit.service;

import com.alibaba.fastjson.JSON;
import com.ihrm.audit.client.FeignClientService;
import com.ihrm.audit.dao.ProcInstanceDao;
import com.ihrm.audit.dao.ProcTaskInstanceDao;
import com.ihrm.audit.dao.ProcUserGroupDao;
import com.ihrm.audit.entity.ProcInstance;
import com.ihrm.audit.entity.ProcTaskInstance;
import com.ihrm.audit.entity.ProcUserGroup;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.User;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class AuditService {

    @Autowired
    private ProcInstanceDao procInstanceDao;

    @Autowired
    private FeignClientService feignClientService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcTaskInstanceDao procTaskInstanceDao;

    @Autowired
    private ProcUserGroupDao procUserGroupDao;

    @Autowired
    private EntityManager entityManager;

    //分页查询申请列表
    public Page getInstanceList(ProcInstance instance, Integer page, Integer size) {
        //使用Specification进行查询
        Specification<ProcInstance> spec = new Specification<ProcInstance>() {
            //构造查询条件
            public Predicate toPredicate(Root<ProcInstance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //审批类型
                if (!StringUtils.isEmpty(instance.getProcessName())){
                    list.add(cb.equal(root.get("processName").as(String.class),instance.getProcessName()));
                }
                //审批状态
                if (!StringUtils.isEmpty(instance.getProcessState())){
                    Expression<String> exp = root.get("processState");
                    list.add(exp.in(instance.getProcessState().split(",")));
                }
                //当前节点的待处理人
                if (!StringUtils.isEmpty(instance.getProcCurrNodeUserId())){
                    list.add(cb.equal(root.get("procCurrNodeUserId").as(String.class),"%" + instance.getProcCurrNodeUserId() + "%"));
                }
                //发起人
                if (!StringUtils.isEmpty(instance.getUserId())){
                    list.add(cb.equal(root.get("userId").as(String.class),instance.getUserId()));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //调用dao进行Specification查询
        return procInstanceDao.findAll(spec, PageRequest.of(page-1,size));
    }

    //根据id查询申请的详细数据
    public ProcInstance findInstanceDetail(String id) {
        return procInstanceDao.findById(id).get();
    }

    //流程申请
    public void startProcess(Map map, String companyId) {
        //1.构造业务数据
        String userId = (String) map.get("userId");
        String processKey = (String) map.get("processKey");
        String processName = (String) map.get("processName");

        User user = feignClientService.getUserById(userId);
        ProcInstance instance = new ProcInstance();
        BeanUtils.copyProperties(user,instance);

        instance.setUserId(userId);
        instance.setProcessId(idWorker.nextId()+"");
        instance.setProcApplyTime(new Date());
        instance.setProcessKey(processKey);
        instance.setProcessName(processName);
        instance.setProcessState("1");  //审批中
        String data = JSON.toJSONString(map);
        instance.setProcData(data);
        //2.查询流程定义
        ProcessDefinition result = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .processDefinitionTenantId(companyId)
                .latestVersion()
                .singleResult();
        //3.开启流程
        Map vars= new HashMap();
        if ("process_leave".equals(processKey)){
            //请假
            vars.put("days",map.get("duration"));
        }
        runtimeService.startProcessInstanceById(result.getId(),instance.getProcessId(),vars);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(result.getId(),instance.getProcessId(),vars);
        //4.自动执行第一个任务节点
        //4.1获取的任务节点
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //4.2执行
        taskService.complete(task.getId());
        //5.获取下一个节点数据，填充业务数据中当前审批人
        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        if (nextTask != null){
            List<User> userList = findCurrUsers(nextTask,user);
            StringBuilder userNameSb = new StringBuilder();
            StringBuilder userIdS = new StringBuilder();
            for (User temp : userList){
                userNameSb.append(temp.getUsername()).append(" ");
                userIdS.append(temp.getId());
            }
            instance.setProcCurrNodeUserId(userIdS.toString());
            instance.setProcCurrNodeUserName(userNameSb.toString());
        }
        procInstanceDao.save(instance);
        ProcTaskInstance pti = new ProcTaskInstance();
        pti.setTaskId(idWorker.nextId() + "");
        pti.setProcessId(instance.getProcessId());
        pti.setHandleTime(new Date());
        pti.setHandleType("2");   //审批通过
        pti.setHandleUserId(userId);
        pti.setHandleUserName(user.getUsername());
        pti.setTaskKey(task.getTaskDefinitionKey());
        pti.setTaskName(task.getName());
        pti.setHandleOpinion("发起申请");
        procTaskInstanceDao.save(pti);
    }


    private List<User> findCurrUsers(Task nextTask,User user) {
        //查询任务的节点数据(候选人组)
        List<IdentityLink> list = taskService.getIdentityLinksForTask(nextTask.getId());
        List<User> users = new ArrayList<>();
        for (IdentityLink identityLink : list) {
            String groupId = identityLink.getGroupId(); //候选人组id
            ProcUserGroup userGroup = procUserGroupDao.findById(groupId).get(); //查询UserGroup
            String param = userGroup.getParam();
            String paramValue = null;
            if ("user_id".equals(param)) {
                paramValue = user.getId();
            }
            else if ("department_id".equals(param)) {
                paramValue = user.getDepartmentId();
            }
            else if ("company_id".equals(param)) {
                paramValue = user.getCompanyId();
            }
            String sql = userGroup.getIsql().replaceAll("\\$\\{" + param + "\\}", paramValue);
            Query query = entityManager.createNativeQuery(sql);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(User.class));
            users.addAll(query.getResultList());
        }
        return users;
    }

    /**
     * 提交审核
     * @param procTaskInstance
     *      handleOpinion: "同意" 操作说明
     *      handleType: "2"   处理类型（2.审批通过；3.审批不通过；4.撤销）
     *      handleUserId: "1491682702800265216"  处理人
     *      processId: "1175967133311934461"  业务流程id
     * @param companyId
     */
    public void commit(ProcTaskInstance procTaskInstance, String companyId) {
        //1.查询业务流程对象
        String processId = procTaskInstance.getProcessId();
        ProcInstance procInstance = procInstanceDao.findById(processId).get();
        //2.设置业务流程状态
        procInstance.setProcessState(procTaskInstance.getHandleType());
        //3.根据不同的操作类型，完成不同的业务处理
        List<ProcessInstance> procInstanceList = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(processId).list();
        User user = feignClientService.getUserById(procTaskInstance.getHandleUserId());
        if ("2".equals(procTaskInstance.getHandleType())){
            //3.1如果通过，完成当前任务
            //查询出当前节点，完成当前节点
            Task task = taskService.createTaskQuery().processInstanceId(procInstanceList.get(0).getId()).singleResult();
            taskService.complete(task.getId());
            //查询出下一个任务节点，如果存在，继续进行
            Task nextTask = taskService.createTaskQuery().processInstanceId(procInstanceList.get(0).getId()).singleResult();
            System.out.println(nextTask);
            if (nextTask != null){
                List<User> users = findCurrUsers(nextTask,user);
                String usernames = "", userIdS = "";
                for (User user1 : users) {
                    usernames += user1.getUsername() + " ";
                    userIdS += user1.getId();
                }
                procInstance.setProcCurrNodeUserId(userIdS);
                procInstance.setProcCurrNodeUserName(usernames);
                procInstance.setProcessState("1");
            }else {
                //如果不存在，任务结束
                procInstance.setProcessState("2");
            }
        }else {
            //3.2如果不通过，或者撤销（删除activiti流程）
            runtimeService.deleteProcessInstance(procInstanceList.get(0).getId(),procTaskInstance.getHandleOpinion());
        }
        //4.更新业务流程对象，保存业务对象
        procInstanceDao.save(procInstance);
        procTaskInstance.setTaskId(idWorker.nextId()+"");
        procTaskInstance.setHandleUserName(user.getUsername());
        procTaskInstance.setHandleTime(new Date());
        procTaskInstanceDao.save(procTaskInstance);
    }

    public List<ProcTaskInstance> findTaskByProcess(String id){
        return procTaskInstanceDao.findByProcessId(id);
    }
}

package com.ihrm.audit.controller;

import com.ihrm.audit.entity.ProcInstance;
import com.ihrm.audit.entity.ProcTaskInstance;
import com.ihrm.audit.service.AuditService;
import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="/user/process")
public class AuditController extends BaseController {

    @Autowired
    private AuditService auditService;

    /**
     * 查询申请列表
     *      业务参数：
     *          审批类型
     *          审批状态
     *          当前节点的待处理人
     */
    @PutMapping("/instance/{page}/{size}")
    public Result instanceList(@RequestBody ProcInstance instance,@PathVariable Integer page,@PathVariable Integer size){
        //进行分页查询
        Page pages = auditService.getInstanceList(instance,page,size);
        //page对象转化为自己的pageResult对象
        PageResult result = new PageResult(pages.getTotalElements(), pages.getContent());
        return new Result(ResultCode.SUCCESS,result);
    }

    /**
     * 查询申请的详细数据
     */
    @PutMapping("/instance/{id}")
    public Result instanceList(@PathVariable String id){
        ProcInstance instance = auditService.findInstanceDetail(id);
        return new Result(ResultCode.SUCCESS,instance);
    }

    /**
     * 流程申请
     */
    @PostMapping("/startProcess")
    public Result startProcess(@RequestBody Map map){
        auditService.startProcess(map,companyId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 提交审核
     *      handleType: 处理类型（2.审批通过；3.审批不通过；4.撤销）
     */
    @PutMapping("/instance/commit")
    public Result commit(@RequestBody ProcTaskInstance procTaskInstance){
        auditService.commit(procTaskInstance,companyId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询流程任务明细
     */
    @GetMapping("/instance/task/{id}")
    public Result tasks(@PathVariable String id){
        return new Result(ResultCode.SUCCESS,auditService.findTaskByProcess(id));
    }
}

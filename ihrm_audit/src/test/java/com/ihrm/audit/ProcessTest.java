package com.ihrm.audit;

import com.ihrm.audit.dao.ProcUserGroupDao;
import com.ihrm.audit.entity.ProcUserGroup;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcessTest {
    /**
     * 注入activiti提供查询的service
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 测试查询所有的流程定义对象
     */
    @Test
    public void testActiviti(){
        //获取流程定义查询对象query
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        //添加查询的条件
        query.processDefinitionTenantId("东莞理工");
        //查询
        List<ProcessDefinition> list = query.latestVersion().list();
        System.out.println(list.size());
    }

    /**
     * 测试流程的挂起与激活
     */
    @Test
    public void testSuspend(){
        //流程挂起
        //repositoryService.suspendProcessDefinitionById("process_leave:1:6b69a256-bbf1-11ec-8725-fea266016db1");

        //流程激活
        repositoryService.activateProcessDefinitionById("process_leave:1:6b69a256-bbf1-11ec-8725-fea266016db1");
    }
}

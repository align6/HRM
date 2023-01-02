package com.ihrm.audit;

import com.ihrm.audit.dao.ProcUserGroupDao;
import com.ihrm.audit.entity.ProcUserGroup;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AuditTest {
    @Autowired
    private ProcUserGroupDao procUserGroupDao;

    @Test
    public void test(){
        List<ProcUserGroup> list = procUserGroupDao.findAll();
        System.out.println(list.size());
    }

    @Test
    public void testzz(){
        int i = 1;
        while(i<=2){
            i = i * 2;
            System.out.println("执行");
        }
    }

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testActiviti(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        System.out.println(list.size());
    }
}

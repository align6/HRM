package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门
     */
    public void save(Department department){
        String id = idWorker.nextId()+"";
        department.setId(id);
        department.setCreateTime(new Date());
        departmentDao.save(department);
    }

    /**
     * 更新部门
     */
    public void update(Department department){
        Department temp = departmentDao.findById(department.getId()).get();
        temp.setDepartmentName(department.getDepartmentName());//部门名称
        temp.setDepartmentCode(department.getDepartmentCode());//部门编码
        temp.setIntroduce(department.getIntroduce());//部门介绍
        departmentDao.save(temp);
    }

    /**
     * 根据id删除部门
     */
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }

    /**
     * 根据id查询部门
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }

    /**
     * 查询所有部门
     */
    public List<Department> findAll(String companyId){
        return departmentDao.findAll(getSpec(companyId));
    }

    /**
     * 根据部门编码和企业id查询部门
     */
    public Department findByCode(String code, String companyId) {
        return departmentDao.findByDepartmentCodeAndCompanyId(code,companyId);
    }
}

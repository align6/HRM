package com.ihrm.employee.dao;

import com.ihrm.domain.employee.EmployeePositive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PositiveDao extends JpaRepository<EmployeePositive, String>, JpaSpecificationExecutor<EmployeePositive> {
    EmployeePositive findByUserId(String userId); //当使用findById.get时，返回null值系统会报错，即无法判断null值来创建新的对象
}

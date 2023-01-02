package com.ihrm.employee.dao;

import com.ihrm.domain.employee.UserCompanyJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCompanyJobsDao extends JpaRepository<UserCompanyJobs,String>, JpaSpecificationExecutor<UserCompanyJobs> {
    UserCompanyJobs findByUserId(String userId); //当使用findById.get时，返回null值系统会报错，即无法判断null值来创建新的对象
}

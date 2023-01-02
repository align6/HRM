package com.ihrm.employee.dao;

import com.ihrm.domain.employee.UserCompanyPersonal;
import com.ihrm.domain.employee.response.EmployeeReportResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCompanyPersonalDao extends JpaRepository<UserCompanyPersonal,String>, JpaSpecificationExecutor<UserCompanyPersonal> {
    UserCompanyPersonal findByUserId(String userId); //当使用findById.get时，返回null值系统会报错，即无法判断null值来创建新的对象

    @Query(value = "select new com.ihrm.domain.employee.response.EmployeeReportResult(a,b) from UserCompanyPersonal a " +
            "left join EmployeeResignation b on a.userId=b.userId where a.companyId=?1 and a.entryTime like ?2 or (" +
            "b.resignationTime like ?2)")
    List<EmployeeReportResult> findByReport(String companyId, String month);
}

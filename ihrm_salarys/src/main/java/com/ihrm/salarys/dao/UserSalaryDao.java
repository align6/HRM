package com.ihrm.salarys.dao;

import com.ihrm.domain.salarys.UserSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserSalaryDao extends JpaRepository<UserSalary,String>, JpaSpecificationExecutor<UserSalary> {

    @Query(nativeQuery = true,
            value="select bu.id,bu.username,bu.mobile,bu.work_number workNumber," +
                    "bu.in_service_status inServiceStatus,bu.department_name departmentName,bu.department_id departmentId,bu.entry_time entryTime ," +
                    "bu.employment_form employeeForm ,sauss.current_basic_salary currentBasicSalary,sauss.current_post_wage currentPostWage from bs_user bu LEFT JOIN sa_user_salary sauss ON bu.id=sauss.user_id WHERE bu.company_id = ?1",
            countQuery = "select count(*) from bs_user bu LEFT JOIN sa_user_salary sauss ON bu.id=sauss.user_id WHERE bu.company_id = ?1"
    )
    Page<Map> findPage(String companyId, PageRequest pageRequest);
}

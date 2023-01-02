package com.ihrm.salarys.dao;

import com.ihrm.domain.salarys.UserSalaryChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSalaryChangeDao extends JpaRepository<UserSalaryChange,String>, JpaSpecificationExecutor<UserSalaryChange> {
}

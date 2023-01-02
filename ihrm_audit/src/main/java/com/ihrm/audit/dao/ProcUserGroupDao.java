package com.ihrm.audit.dao;

import com.ihrm.audit.entity.ProcUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcUserGroupDao extends JpaRepository<ProcUserGroup,String>, JpaSpecificationExecutor<ProcUserGroup> {
}

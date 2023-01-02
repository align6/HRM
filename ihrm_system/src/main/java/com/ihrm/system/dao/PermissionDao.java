package com.ihrm.system.dao;

import com.ihrm.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {
    List<Permission> findByTypeAndParentId(int type,String pid);
}

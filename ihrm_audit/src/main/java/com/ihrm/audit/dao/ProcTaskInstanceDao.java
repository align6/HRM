package com.ihrm.audit.dao;

import com.ihrm.audit.entity.ProcTaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcTaskInstanceDao extends JpaRepository<ProcTaskInstance,String>, JpaSpecificationExecutor<ProcTaskInstance> {
    ProcTaskInstance findByProcessIdAndTaskKey(String processId, String taskKey);

    List<ProcTaskInstance> findByProcessId(String processId);
}

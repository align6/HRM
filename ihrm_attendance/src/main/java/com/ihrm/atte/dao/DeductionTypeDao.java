package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.DeductionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeductionTypeDao extends JpaRepository<DeductionType,String>, JpaSpecificationExecutor<DeductionType> {
}

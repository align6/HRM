package com.ihrm.company.dao;

import com.ihrm.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 自定义dao接口继承
 *  JpaRepository<实体类，主键>增删改查
 *  JpaSpecificationExecutor<实体类>多条件查询
 */
@Repository
public interface CompanyDao extends JpaRepository<Company,String>, JpaSpecificationExecutor<Company> {
}

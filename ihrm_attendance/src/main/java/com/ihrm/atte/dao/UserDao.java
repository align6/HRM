package com.ihrm.atte.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * 直接将userDao写在这，不需要跨微服务，避免数据量过大
 */
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    User findByMobile(String mobile);

    List<User> findByCompanyId(String companyId);

    @Query(value="select * from bs_user where company_id=?1",nativeQuery = true)
    Page<User> findPage(String companyId, Pageable pageable);
}

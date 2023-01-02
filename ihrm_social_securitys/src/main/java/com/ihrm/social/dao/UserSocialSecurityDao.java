package com.ihrm.social.dao;

import com.ihrm.domain.social_securitys.UserSocialSecurity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserSocialSecurityDao extends JpaRepository<UserSocialSecurity, String>, JpaSpecificationExecutor<UserSocialSecurity> {
    @Query(nativeQuery = true,
            value="SELECT bu.id userId,\n" +
                    "       bu.username,\n" +
                    "       bu.mobile,\n" +
                    "       bu.work_number  workNumber,\n" +
                    "       bu.department_name departmentName,\n" +
                    "       bu.entry_time entryTime,\n" +
                    "       ss.participating_in_the_city participatingInTheCity, \n" +
                    "       ss.provident_fund_city providentFundCity,\n" +
                    "       ss.social_security_base socialSecurityBase,\n" +
                    "       ss.provident_fund_base providentFundBase FROM bs_user bu LEFT JOIN ss_user_social_security ss ON bu.id=ss.user_id WHERE bu.company_id=1",
            countQuery = "SELECT COUNT(*) FROM bs_user u LEFT JOIN ss_user_social_security s ON u.id = s.user_id WHERE company_id=?1"
    )
    public Page<Map> findPage(String companyId, Pageable pageable);
}

package com.ihrm.social.dao;

import com.ihrm.domain.social_securitys.PaymentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentItemDao extends JpaRepository<PaymentItem,String>, JpaSpecificationExecutor<PaymentItem> {
}

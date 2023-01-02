package com.ihrm.social.dao;

import com.ihrm.domain.social_securitys.CityPaymentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityPaymentItemDao extends JpaRepository<CityPaymentItem,String>, JpaSpecificationExecutor<CityPaymentItem> {
    List<CityPaymentItem> findAllByCityId(String id);
}

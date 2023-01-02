package com.ihrm.social.service;

import com.ihrm.domain.social_securitys.CityPaymentItem;
import com.ihrm.social.dao.CityPaymentItemDao;
import com.ihrm.social.dao.PaymentItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentItemService {
    @Autowired
    private CityPaymentItemDao cityPaymentItemDao;

    /**
     * 根据城市id查询参保项目
     */
    public List<CityPaymentItem> findAllByCityId(String id) {
        return cityPaymentItemDao.findAllByCityId(id);
    }
}

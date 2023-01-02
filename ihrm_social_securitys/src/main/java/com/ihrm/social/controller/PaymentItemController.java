package com.ihrm.social.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.social_securitys.CityPaymentItem;
import com.ihrm.social.service.PaymentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social_securitys")
@CrossOrigin
public class PaymentItemController extends BaseController {
    @Autowired
    private PaymentItemService paymentItemService;

    /**
     * 根据城市id查询参保项目
     */
    @GetMapping("/payment_item/{id}")
    public Result findPaymentItemByCityId(@PathVariable String id){
        List<CityPaymentItem> list = paymentItemService.findAllByCityId(id);
        return new Result(ResultCode.SUCCESS,list);
    }
}

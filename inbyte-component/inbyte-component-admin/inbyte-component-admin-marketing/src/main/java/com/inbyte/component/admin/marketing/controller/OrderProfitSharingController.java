package com.inbyte.component.admin.marketing.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.service.OrderProfitSharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单分账
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@RestController
@RequestMapping("marketing/order/profit/sharing")
public class OrderProfitSharingController {

    @Autowired
    private OrderProfitSharingService marketingDistributorService;


    /**
     * 创建
     *
     * @return R
     **/
    @PostMapping
    public R share() {
        return marketingDistributorService.share();
    }

}

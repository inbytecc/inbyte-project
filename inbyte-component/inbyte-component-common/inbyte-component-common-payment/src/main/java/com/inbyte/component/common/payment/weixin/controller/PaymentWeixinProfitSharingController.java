package com.inbyte.component.common.payment.weixin.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.payment.weixin.service.PaymentWeixinPartnerProfitSharingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单分账
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@RestController
@RequestMapping("payment/weixin/profit/sharing")
@Slf4j
public class PaymentWeixinProfitSharingController {

    @Autowired
    private PaymentWeixinPartnerProfitSharingService paymentWeixinPartnerProfitSharingService;

    /**
     * 分账成功回调
     *
     * @return R
     **/
    @PostMapping("notify/success")
    public R sharingSuccess(@RequestBody String data) {
        log.info("分账成功回调请求：{}", data);
        return R.ok();
    }

    /**
     * 分账解冻回调
     *
     * @return R
     **/
    @PostMapping("notify/unfreeze")
    public R unfreezeSuccess(@RequestBody String data) {
        log.info("分账解冻回调请求：{}", data);
        return R.ok();
    }


    /**
     * 分账解冻回调
     *
     * @return R
     **/
    @PostMapping("{orderNo}/unfreeze")
    public R unfreeze(@PathVariable String orderNo) {
        log.info("分账解冻请求：{}", orderNo);
        return paymentWeixinPartnerProfitSharingService.unfreeze(orderNo);
    }
}

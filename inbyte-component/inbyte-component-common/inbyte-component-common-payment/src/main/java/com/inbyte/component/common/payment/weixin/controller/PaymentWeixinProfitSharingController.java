package com.inbyte.component.common.payment.weixin.controller;

import com.inbyte.commons.model.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 分账回调
     *
     * @return R
     **/
    @PostMapping("success")
    public R success(@RequestBody String data) {
        log.info("分账回调请求：{}", data);
        return R.ok();
    }


}

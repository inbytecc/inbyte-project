package com.inbyte.component.common.payment.weixin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 微信支付回调返回值
 *
 * @author chenjw
 * @date 2022/11/21
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentWeixinResult {

    /**
     * 错误码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;
}

package com.inbyte.component.common.payment.weixin.model;

import lombok.*;

/**
 * 微信预付单商户所需传递参数
 *
 * @author chenjw
 * @date 2022/11/14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundWeixinSuccessVerifyParam {

    /**
     * 微信支付商户ID
     */
    private String weixinPaymentMerchantId;

    /**
     * 请求体数据
     */
    private String requestBodyString;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 随机字符串
     */
    private String nonce;
    /**
     * 签名
     */
    private String signature;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 证书序列号
     */
    private String wechatPayCertificateSerialNumber;
}

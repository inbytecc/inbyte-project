package com.inbyte.component.common.payment.weixin.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

/**
 * 小程序调起微信支付参数
 *
 * @Author: chenjw
 * @Data: 2022/11/1 14:44
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentWeixinPrepayDto {
    /**
     * 时间戳
     * 是否必填:是
     * 当前的时间, 其他详见时间戳规则。
     */
    private String timeStamp;
    /**
     * 随机字符串
     * 是否必填:是
     * 随机字符串, 不长于32位。
     */
    private String nonceStr;

    /**
     *订单详情扩展字符串
     * 是否必填:是
     *小程序下单接口返回的prepay_id参数值, 提交格式如：prepay_id=****/
    @JsonSetter("package1")
    private String packageVal;

    /**
     * 签名方式
     * 是否必填:是
     * 签名类型, 默认为RSA, 仅支持RSA。
     */
    private String signType;
    /**
     * 签名
     * 是否必填:是
     * 签名, 使用字段appId、timeStamp、nonceStr、package计算得出的签名值
     */
    private String paySign;
}

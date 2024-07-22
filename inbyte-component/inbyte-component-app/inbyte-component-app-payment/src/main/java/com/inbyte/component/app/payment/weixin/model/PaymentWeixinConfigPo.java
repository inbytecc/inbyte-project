package com.inbyte.component.app.payment.weixin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 微信支付配置实体
 *
 * @author chenjw
 * @date 2024-04-26 13:54:47
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("payment_weixin_config")
public class PaymentWeixinConfigPo {

    /**
      * 微信支付商户ID
      */
    @TableId(value = "weixin_payment_merchant_id", type = IdType.AUTO)
    private String weixinPaymentMerchantId;

    /**
      * 服务商支付
      */
    private Integer partnerPay;

    /**
      * app名称
      */
    private String apiV3Key;

    /**
      * 商户证书序列号
      */
    private String serialNumber;

    /**
      * 商户私钥 apiclient_key.pem 内容
      */
    private String merchantPrivateKey;

    /**
      * 手续费
      */
    private String serviceChargeRate;

    /**
      * 备注
      */
    private String remark;

}

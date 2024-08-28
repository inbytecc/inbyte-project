package com.inbyte.component.common.payment.weixin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 营地支付配置实体
 *
 * @author chenjw
 * @date 2024-05-20 16:48:37
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("payment_venue")
public class PaymentVenuePo {

    /**
      * 营地ID
      */
    @TableId(value = "venue_id", type = IdType.AUTO)
    private String venueId;

    /**
      * 默认微信支付ID
      */
    private String weixinPaymentId;

    /**
      * 默认支付宝支付ID
      */
    private String aliPaymentId;

    /**
      * 微信订单支付配置
      */
    private List<PaymentConfigItem> weixinOrderPaymentConfig;

    /**
      * 支付宝订单支付配置
      */
    private List<PaymentConfigItem> aliOrderPaymentConfig;

    /**
      * 商户号
      */
    private String mctNo;

    /**
      * 备注
      */
    private String remark;

    /**
      * 更新时间
      */
    private LocalDateTime updateTime;

    /**
      * 修改人
      */
    private String modifier;

}

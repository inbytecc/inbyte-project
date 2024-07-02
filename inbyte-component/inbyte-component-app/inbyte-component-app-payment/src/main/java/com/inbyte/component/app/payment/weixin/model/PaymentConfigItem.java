package com.inbyte.component.app.payment.weixin.model;

import com.inbyte.commons.model.enums.OrderTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 营地支付配置实体
 *
 * @author chenjw
 * @date 2024-05-20 16:48:37
 **/
@Getter
@Setter
public class PaymentConfigItem {

    /**
     * 订单类型
     */
    private OrderTypeEnum orderType;

    /**
     * 支付配置ID
     */
    private String paymentMctId;
}

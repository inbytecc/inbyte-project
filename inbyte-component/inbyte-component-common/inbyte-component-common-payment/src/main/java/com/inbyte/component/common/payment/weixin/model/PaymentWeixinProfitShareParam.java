package com.inbyte.component.common.payment.weixin.model;

import com.inbyte.commons.model.enums.AccountTypeEnum;
import com.inbyte.commons.model.enums.OrderTypeEnum;
import lombok.*;

import java.math.BigDecimal;

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
public class PaymentWeixinProfitShareParam {


    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单描述
     */
    private String orderBrief;
    /**
     * 订单类型
     */
    private OrderTypeEnum orderType;

    /**
     * 金额
     */
    private BigDecimal shareAmount;

    /** 分销员类型 */
    private AccountTypeEnum accountType;

    /** 接受分账账户 */
    private String receiverAccount;

    /** 接受分账姓名 */
    private String receiverName;


}

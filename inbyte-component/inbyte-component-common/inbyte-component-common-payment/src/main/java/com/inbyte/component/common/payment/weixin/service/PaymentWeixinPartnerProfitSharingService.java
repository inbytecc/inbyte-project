package com.inbyte.component.common.payment.weixin.service;

import com.inbyte.commons.model.dto.R;
import com.wechat.pay.java.service.profitsharing.model.AddReceiverRequest;

/**
 * 微信合作伙伴服务商支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
public interface PaymentWeixinPartnerProfitSharingService {


    R profitShare();

    R addReceiver(AddReceiverRequest param);
}

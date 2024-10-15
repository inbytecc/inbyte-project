package com.inbyte.component.admin.marketing.service.impl;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.service.OrderProfitSharingService;
import com.inbyte.component.common.payment.weixin.service.impl.PaymentWeixinPartnerProfitSharingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单分账
 *
 * @author chenjw
 * @date 2024-10-15
 */
@Service
public class OrderProfitSharingServiceImpl implements OrderProfitSharingService {

    @Autowired
    private PaymentWeixinPartnerProfitSharingServiceImpl service;

    @Override
    public R share() {
        service.profitShare();
        return R.ok();
    }
}

package com.inbyte.component.common.payment.weixin.service.impl;

import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinRefundMapper;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.profitsharing.ProfitsharingService;
import com.wechat.pay.java.service.profitsharing.model.CreateOrderReceiver;
import com.wechat.pay.java.service.profitsharing.model.CreateOrderRequest;
import com.wechat.pay.java.service.profitsharing.model.OrdersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信合作伙伴服务商支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
@Service
@Slf4j
public class PaymentWeixinPartnerProfitSharingServiceImpl implements InitializingBean {

    private BigDecimal ONE_HUNDRED = new BigDecimal(100);


    @Value("${inbyte.app.server}")
    private String appServer;

    @Autowired
    private PaymentWeixinInfoMapper paymentWeixinInfoMapper;
    @Autowired
    private PaymentWeixinRefundMapper refundMapper;
    @Autowired
    private SystemAlarm alarmSystemClient;
    @Autowired
    private PaymentWeixinPartnerConfig paymentWeixinPartnerConfig;


    private ProfitsharingService profitsharingService;

    /**
     * 初始化配置
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        RSAAutoCertificateConfig config = paymentWeixinPartnerConfig.getConfig();

        // 初始化服务
        profitsharingService = new ProfitsharingService.Builder().config(config).build();
    }

    public R profitShare() {
        List<CreateOrderReceiver> receivers = new ArrayList();
        CreateOrderReceiver orderReceiver = new CreateOrderReceiver();
        orderReceiver.setType("PERSONAL_SUB_OPENID");
        orderReceiver.setAccount("oS1t77bmtVbex2iZND9CKWIfvfA0");
//        orderReceiver.setName("UNCLEKEVIN");
        orderReceiver.setDescription("分账描述");
        orderReceiver.setAmount(100L);
        receivers.add(orderReceiver);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setAppid(paymentWeixinPartnerConfig.getAppId());
        request.setSubMchid("1683232124");
        request.setSubAppid("wxce4ba383495d7553");
        request.setTransactionId("4200002359202410150075395592");
        request.setOutOrderNo("CMP151531001246811011");
        request.setReceivers(receivers);
        request.setUnfreezeUnsplit(false);
        request.setNotifyUrl(appServer + "/api/payment/weixin/profit/sharing/success");

        OrdersEntity ordersEntity = profitsharingService.createOrder(request);
        log.info("分账结果：{}", ordersEntity);
        return R.ok();
    }

}

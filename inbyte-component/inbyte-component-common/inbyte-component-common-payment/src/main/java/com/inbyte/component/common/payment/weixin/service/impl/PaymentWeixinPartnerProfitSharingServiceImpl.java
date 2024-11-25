package com.inbyte.component.common.payment.weixin.service.impl;

import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.enums.AccountTypeEnum;
import com.inbyte.commons.util.ArithUtil;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinRefundMapper;
import com.inbyte.component.common.payment.weixin.model.PaymentWeixinInfoBrief;
import com.inbyte.component.common.payment.weixin.model.PaymentWeixinProfitShareParam;
import com.inbyte.component.common.payment.weixin.service.PaymentWeixinPartnerProfitSharingService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.profitsharing.ProfitsharingService;
import com.wechat.pay.java.service.profitsharing.model.*;
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
public class PaymentWeixinPartnerProfitSharingServiceImpl implements PaymentWeixinPartnerProfitSharingService, InitializingBean {

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

    public R profitShare(PaymentWeixinProfitShareParam param) {
        List<CreateOrderReceiver> receivers = new ArrayList();
        CreateOrderReceiver orderReceiver = new CreateOrderReceiver();
        String type;
        if (param.getAccountType() == AccountTypeEnum.MERCHANT) {
            return R.failure("暂不支持商户分账");
        } else {
            type = "PERSONAL_SUB_OPENID";
        }
        orderReceiver.setType(type);
        orderReceiver.setAccount(param.getReceiverAccount());
        orderReceiver.setName(param.getReceiverName());
        orderReceiver.setDescription(param.getOrderType().name + " - " + param.getOrderBrief() + " - 分账" + param.getShareAmount());
        orderReceiver.setAmount(ArithUtil.multiply(param.getShareAmount(), new BigDecimal(100)).longValue());
        receivers.add(orderReceiver);

        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(param.getOrderNo());
        if (paymentWeixinInfoBrief == null) {
            return R.error("订单不存在");
        }


        CreateOrderRequest request = new CreateOrderRequest();
        request.setAppid(paymentWeixinPartnerConfig.getAppId());
        request.setSubMchid(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());
        request.setSubAppid(paymentWeixinInfoBrief.getAppId());
        request.setTransactionId(paymentWeixinInfoBrief.getPaymentNo());
        request.setOutOrderNo(param.getOrderNo());
        request.setReceivers(receivers);
        request.setUnfreezeUnsplit(false);
        request.setNotifyUrl(appServer + "/api/payment/weixin/profit/sharing/notify/success");

        OrdersEntity ordersEntity = profitsharingService.createOrder(request);
        log.info("分账结果：{}", ordersEntity);
        return R.ok();
    }

    @Override
    public R addReceiver(AddReceiverRequest param) {
        param.setAppid(paymentWeixinPartnerConfig.getAppId());
        AddReceiverResponse addReceiverResponse = profitsharingService.addReceiver(param);
        log.info("添加分账接收方结果：{}", addReceiverResponse);
        return R.ok();
    }

    @Override
    public R unfreeze(String orderNo) {
        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(orderNo);
        if (paymentWeixinInfoBrief != null) {
            UnfreezeOrderRequest request = new UnfreezeOrderRequest();
            request.setTransactionId(paymentWeixinInfoBrief.getPaymentNo());
            request.setOutOrderNo(orderNo);
            request.setDescription("解除分账冻结");
            request.setSubMchid(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());
            request.setNotifyUrl(appServer + "/api/payment/weixin/profit/sharing/notify/unfreeze");
            profitsharingService.unfreezeOrder(request);
        }
        return R.ok("分账解冻指令发送成功");
    }

}

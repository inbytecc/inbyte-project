package com.inbyte.component.app.payment.weixin.service;

import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.ArithUtil;
import com.inbyte.component.app.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.app.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.app.payment.weixin.dao.PaymentWeixinConfigMapper;
import com.inbyte.component.app.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.app.payment.weixin.model.*;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.cipher.Signer;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.core.util.NonceUtil;
import com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.*;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信合作伙伴服务商支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
@Service
@Slf4j
public class PaymentWeixinPartnerService {

    @Value("${inbyte.app.server}")
    private String appServer;

    @Autowired
    private PaymentWeixinInfoMapper paymentWeixinInfoMapper;
    @Autowired
    private PaymentWeixinConfigMapper paymentWeixinConfigMapper;
    @Autowired
    private SystemAlarm alarmSystemClient;

    private ConcurrentHashMap<String, RSAAutoCertificateConfig> WeiXin_Payment_Config_Map = new ConcurrentHashMap<>();

    private static final String PARTNER_ID = "xx";

    /**
     * 获取微信支付配置
     *
     * @param partnerId 服务商商户号
     * @return RSAAutoCertificateConfig 配置
     */
    private synchronized RSAAutoCertificateConfig getConfig(String partnerId) {
        RSAAutoCertificateConfig rsaAutoCertificateConfig = WeiXin_Payment_Config_Map.get(partnerId);
        if (rsaAutoCertificateConfig != null) {
            return rsaAutoCertificateConfig;
        }

        PaymentWeixinConfigPo paymentConfigBrief = paymentWeixinConfigMapper.selectById(partnerId);
        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(partnerId)
                .privateKey(paymentConfigBrief.getMerchantPrivateKey())
                .merchantSerialNumber(paymentConfigBrief.getSerialNumber())
                .apiV3Key(paymentConfigBrief.getApiV3Key())
                .build();
        WeiXin_Payment_Config_Map.put(partnerId, config);

        return config;
    }

    private JsapiService getJsApiService(String partnerId) {
        return new JsapiService.Builder().config(getConfig(partnerId)).build();
    }

    /**
     * 发起预支付订单
     *
     * @param prepaidOrderParam 预支付订单参数
     * @return 预支付订单响应
     */
    public R<PaymentWeixinPrepayDto> prepayOrder(PaymentWeixinPrepayParam prepaidOrderParam) {
        JsapiService service = getJsApiService(PARTNER_ID);

        String orderDesc = prepaidOrderParam.getOrderBrief().length() >= 32 ? prepaidOrderParam.getOrderBrief().substring(0, 32) : prepaidOrderParam.getOrderBrief();
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(prepaidOrderParam.getPaymentAmount().multiply(BigDecimal.valueOf(100)).intValue());
        request.setAmount(amount);
//        request.setAppid(prepaidOrderParam.getAppId());
//        request.setMchid(PARTNER_ID); // 服务商商户号
//        request.setSubMchid(prepaidOrderParam.getSubMchId()); // 子商户号
        request.setDescription(orderDesc);
//        request.setNotifyUrl(String.format(appServer + "/api/payment/weixin/%s/notify/payment-success", prepaidOrderParam.getPartnerId()));
        request.setOutTradeNo(prepaidOrderParam.getOrderNo());
        Payer payer = new Payer();
        payer.setSpOpenid(prepaidOrderParam.getOpenId());
        request.setPayer(payer);

        try {
            PrepayResponse response = service.prepay(request);
            return requestPayment(prepaidOrderParam, response.getPrepayId());
        } catch (ServiceException e) {
            log.warn("生成微信支付异常, 支付拉起参数{}, 异常信息:{}", JSON.toJSONString(prepaidOrderParam), e);
            alarmSystemClient.alert("支付服务异常", JSON.toJSONString(prepaidOrderParam) + "异常信息:" + e.getMessage());
            return R.failure("支付拉起失败, 请稍后再试");
        }
    }

    /**
     * 获取小程序调起微信支付的参数, 返回给前端调起微信支付
     *
     * @param paymentWeixinPrepayParam 支付参数
     * @param prepayId 预支付ID
     * @return 微信支付参数
     */
    private R<PaymentWeixinPrepayDto> requestPayment(PaymentWeixinPrepayParam paymentWeixinPrepayParam, String prepayId) {
        Signer signer = getConfig(PARTNER_ID).createSigner();
        long timestamp = Instant.now().getEpochSecond();
        String nonceStr = NonceUtil.createNonce(32);
        String packageVal = "prepay_id=" + prepayId;
        String message = paymentWeixinPrepayParam.getAppId() + "\n" + timestamp + "\n" + nonceStr + "\n" + packageVal + "\n";
        String sign = signer.sign(message).getSign();

        PaymentWeixinPrepayDto requestPayment = PaymentWeixinPrepayDto.builder()
                .timeStamp(String.valueOf(timestamp))
                .nonceStr(nonceStr)
                .packageVal(packageVal)
                .paySign(sign)
                .signType("RSA")
                .build();

        return R.ok(requestPayment);
    }

    /**
     * 微信支付异步通知
     * 对微信的支付回调进行验签, 以及处理相关的业务逻辑
     *
     * @param param 微信支付回调参数
     * @return 支付回调处理结果
     */
    public R<PaymentSuccessNotifyParam> paymentSuccessVerify(PaymentWeixinSuccessVerifyParam param) {
        log.info("收到微信支付成功通知, 通知参数:{}", JSON.toJSONString(param));

        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(param.getWechatPayCertificateSerialNumber())
                .nonce(param.getNonce())
                .signature(param.getSignature())
                .timestamp(param.getTimestamp())
                .signType(param.getSignType())
                .body(param.getRequestBodyString())
                .build();

        NotificationParser parser = new NotificationParser(getConfig(PARTNER_ID));
        try {
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            log.info("微信支付成功回调通知, 解析报文：{}", transaction);

            if (transaction.getTradeState() != Transaction.TradeStateEnum.SUCCESS) {
                log.warn("微信支付回调状态:{}, 放弃业务处理", transaction.getTradeState());
                return R.failure("微信支付回调状态非支付成功");
            }

            PaymentWeixinInfoBrief brief = paymentWeixinInfoMapper.selectByNo(transaction.getOutTradeNo());
            if (brief == null) {
                log.warn("微信支付单不存在, 支付回调验证失败, 参数:{}", JSON.toJSONString(param));
                return R.failure("微信支付单不存在, 支付回调验证失败");
            }

            BigDecimal paymentAmount = ArithUtil.divide(transaction.getAmount().getTotal(), 100);
            PaymentWeixinInfoPo paymentWeixinInfoPo = PaymentWeixinInfoPo.builder()
                    .weixinPaymentId(brief.getWeixinPaymentId())
                    .notifyPaymentAmount(paymentAmount)
                    .paymentNo(transaction.getTransactionId())
                    .paymentUserId(transaction.getPayer().getSubOpenid())
                    .paymentFinishTime(LocalDateTime.now())
                    .paid(Whether.Yes)
                    .bankType(transaction.getBankType())
                    .tradeType(transaction.getTradeType().name())
                    .moreInfo(JSON.parseObject(JSON.toJSONString(transaction)))
                    .build();
            paymentWeixinInfoMapper.updateById(paymentWeixinInfoPo);
            log.info("微信支付回调更新参数:{}, 响应结果:{}", JSON.toJSONString(paymentWeixinInfoPo), paymentWeixinInfoPo);

            PaymentSuccessNotifyParam paymentSuccessDto = PaymentSuccessNotifyParam.builder()
                    .appId(brief.getAppId())
                    .orderNo(transaction.getOutTradeNo())
                    .paymentMerchantId(brief.getWeixinPaymentMerchantId())
                    .paymentNo(transaction.getTransactionId())
                    .paymentAmount(paymentAmount)
                    .paymentUserId(transaction.getPayer().getSubOpenid())
                    .paymentTime(LocalDateTime.now())
                    .build();

            return R.ok(paymentSuccessDto);
        } catch (Exception e) {
            log.error("微信验签不通过", e);
            return R.failure("验签不通过");
        }
    }

    public R close(String orderNo) {
        return R.failure("开发中");
    }

    public R<PaymentSuccessNotifyParam> queryPaymentStatus(String orderNo) {
        return R.failure("开发中");
    }
    public R<Refund> refundApply(RefundCommonApplyParam param) {
        return R.failure("开发中");

    }

    public R<RefundSuccessNotifyParam> refundSuccessVerify(RefundWeixinSuccessVerifyParam param) {
        return R.failure("开发中");
    }
}

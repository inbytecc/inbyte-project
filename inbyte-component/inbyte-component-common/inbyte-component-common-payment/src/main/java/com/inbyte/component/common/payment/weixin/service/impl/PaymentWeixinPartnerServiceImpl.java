package com.inbyte.component.common.payment.weixin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.ArithUtil;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.common.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.common.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.common.payment.weixin.InbytePaymentWeixinPartnerProperties;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinConfigMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.common.payment.weixin.model.*;
import com.inbyte.component.common.payment.weixin.service.PaymentWeixinServiceApi;
import com.wechat.pay.java.core.Config;
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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 微信合作伙伴服务商支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
@Service
@Slf4j
public class PaymentWeixinPartnerServiceImpl implements PaymentWeixinServiceApi, InitializingBean {

    private BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 微信支付签署
     */
    private Signer signer;

    @Value("${inbyte.app.server}")
    private String appServer;

    @Autowired
    private PaymentWeixinInfoMapper paymentWeixinInfoMapper;
    @Autowired
    private PaymentWeixinConfigMapper paymentWeixinConfigMapper;
    @Autowired
    private SystemAlarm alarmSystemClient;
    @Autowired
    private InbytePaymentWeixinPartnerProperties inbytePaymentWeixinPartnerProperties;


    public static JsapiService service;

    // https://github.com/wechatpay-apiv3/wechatpay-java/blob/main/service/src/example/java/com/wechat/pay/java/service/partnerpayments/jsapi/JsapiServiceExample.java
    // 支付案例

    /**
     * 初始化配置
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (inbytePaymentWeixinPartnerProperties == null) {
            return;
        }

        // 初始化商户配置
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(inbytePaymentWeixinPartnerProperties.getMerchantId())
                        // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
//                        .privateKeyFromPath(privateKeyPath)
                        .privateKey(inbytePaymentWeixinPartnerProperties.getPrivateKey())
                        .merchantSerialNumber(inbytePaymentWeixinPartnerProperties.getSerialNumber())
                        .apiV3Key(inbytePaymentWeixinPartnerProperties.getApiV3Key())
                        .build();

        // 初始化服务
        service = new JsapiService.Builder().config(config).build();

        signer = config.createSigner();
    }

    /**
     * 发起预支付订单
     *
     * @param prepayParam 预支付订单参数
     * @return 预支付订单响应
     */
    public R<PaymentWeixinPrepayDto> prepayOrder(PaymentWeixinPrepayParam prepayParam, String weixinPaymentId) {
        String notifyUrl;
        if (StringUtil.isEmpty(prepayParam.getAppServer())) {
            notifyUrl = String.format(appServer + "/api/payment/weixin/%s/notify/payment-success", weixinPaymentId);
        } else {
            notifyUrl = String.format(prepayParam.getAppServer() + "/api/payment/weixin/%s/notify/payment-success", weixinPaymentId);
        }

        // 使用自动更新平台证书的RSA配置
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(ArithUtil.multiply(prepayParam.getPaymentAmount(), ONE_HUNDRED).intValue());
        request.setAmount(amount);
        request.setSpAppid("wx96bb1723f57649e0");
        request.setSpMchid("1672917102");
        request.setSubAppid(prepayParam.getAppId());
        request.setSubMchid(weixinPaymentId);
        Payer payer = new Payer();
        payer.setSubOpenid(prepayParam.getOpenId());
        request.setPayer(payer);
        request.setDescription(prepayParam.getOrderBrief());
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(prepayParam.getOrderNo());
        // 调用下单方法，得到应答
        // 获取微信预支付ID
        try {
            PrepayResponse response = service.prepay(request);
            return requestPayment(prepayParam, response.getPrepayId(), weixinPaymentId);
        } catch (ServiceException e) {
            if ("ORDERPAID".equals(e.getErrorCode())) {
                return R.failure("该订单已支付, 请稍等片刻订单状态将恢复正常");
            }

            log.warn("生成微信支付异常, 支付拉起参数{}, 异常信息:{}", JSON.toJSONString(prepayParam), e);
            alarmSystemClient.alert("支付服务异常", JSON.toJSONString(prepayParam)
                    + "异常信息:" + Throwables.getStackTraceAsString(e));
            if ("APPID_MCHID_NOT_MATCH".equals(e.getErrorCode())) {
                return R.failure("服务器支付服务维护中, 请稍后 1 分钟重试");
            }
            return R.failure("支付拉起失败, 请稍后再试");
        }
    }

    /**
     * 获取小程序调起微信支付的参数, 返回给前端调起微信支付
     *
     * @param paymentWeixinPrepayParam 支付参数
     * @param prepayId                 预支付ID
     * @return 微信支付参数
     */
    private R<PaymentWeixinPrepayDto> requestPayment(PaymentWeixinPrepayParam paymentWeixinPrepayParam, String prepayId, String weixinPaymentId) {
        // 创建微信支付信息数据
        PaymentWeixinInfoPo paymentWeixinInfoPo = PaymentWeixinInfoPo.builder()
                .userId(paymentWeixinPrepayParam.getUserId())
                .orderNo(paymentWeixinPrepayParam.getOrderNo())
                .orderBrief(paymentWeixinPrepayParam.getOrderBrief())
                .orderType(paymentWeixinPrepayParam.getOrderType())
                .partnerPayment(Whether.Yes)
                .mainPhoto(paymentWeixinPrepayParam.getMainPhoto())
                .venueId(paymentWeixinPrepayParam.getVenueId())
                .mctNo(paymentWeixinPrepayParam.getMctNo())
                .appId(paymentWeixinPrepayParam.getAppId())
                .paymentAmount(paymentWeixinPrepayParam.getPaymentAmount())
                .weixinPaymentMerchantId(weixinPaymentId)
                .paid(Whether.No)
                .prepayId(prepayId)
                .createTime(LocalDateTime.now())
                .build();
        int insert = paymentWeixinInfoMapper.insertSelective(paymentWeixinInfoPo);
        log.info("微信支付数据创建参数:{}, 响应结果:{}", JSON.toJSONString(paymentWeixinInfoPo), insert);

        long timestamp = Instant.now().getEpochSecond();
        String nonceStr = NonceUtil.createNonce(32);
        String packageVal = "prepay_id=" + prepayId;
        String message = paymentWeixinPrepayParam.getAppId() + "\n" + timestamp + "\n" + nonceStr + "\n" + packageVal + "\n";
        log.debug("Message for RequestPayment signatures is[{}]", message);
        String sign = signer.sign(message).getSign();

        PaymentWeixinPrepayDto requestPayment = PaymentWeixinPrepayDto.builder()
                .timeStamp(String.valueOf(timestamp))
                .nonceStr(nonceStr)
                .package1(packageVal)
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

        NotificationParser parser = new NotificationParser();
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

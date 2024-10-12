package com.inbyte.component.common.payment.weixin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Throwables;
import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.exception.BizException;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.ArithUtil;
import com.inbyte.commons.util.IdentityGenerator;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.common.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.common.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.common.payment.weixin.InbytePaymentWeixinPartnerProperties;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinRefundMapper;
import com.inbyte.component.common.payment.weixin.model.*;
import com.inbyte.component.common.payment.weixin.service.PaymentWeixinService;
import com.inbyte.component.common.payment.weixin.service.PaymentWeixinServiceApi;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.cipher.Signer;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.core.util.NonceUtil;
import com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.*;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    private PaymentWeixinRefundMapper refundMapper;
    @Autowired
    private SystemAlarm alarmSystemClient;
    @Autowired
    private InbytePaymentWeixinPartnerProperties inbytePaymentWeixinPartnerProperties;

    /**
     * JSApi支付服务
     */
    private JsapiService jsapiService;

    /**
     * 退款服务
     */
    private RefundService refundService;

    /**
     * 回调通知解析
     */
    private NotificationParser notificationParser;

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
        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                        .merchantId(inbytePaymentWeixinPartnerProperties.getMerchantId())
                        // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
//                        .privateKeyFromPath(privateKeyPath)
                        .privateKey(inbytePaymentWeixinPartnerProperties.getPrivateKey())
                        .merchantSerialNumber(inbytePaymentWeixinPartnerProperties.getSerialNumber())
                        .apiV3Key(inbytePaymentWeixinPartnerProperties.getApiV3Key())
                        .build();

        // 初始化服务
        jsapiService = new JsapiService.Builder().config(config).build();

        // 支付签名
        signer = config.createSigner();

        refundService = new RefundService.Builder().config(config).build();

        notificationParser = new NotificationParser(config);
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
            PrepayResponse response = jsapiService.prepay(request);
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

        try {
            Transaction transaction = notificationParser.parse(requestParam, Transaction.class);
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
                    .paymentTime(PaymentWeixinService.dateFormatConvert(transaction.getSuccessTime()))
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
        PaymentWeixinInfoBrief paymentInfoBrief = paymentWeixinInfoMapper.selectByNo(param.getOrderNo());
        if (paymentInfoBrief == null) {
            return R.ok("交易不存在, 无法申请退款");
        }
        if (param.getRefundAmount().compareTo(paymentInfoBrief.getPaymentAmount()) == 1) {
            log.info("退款申请金额不能大于支付金额退款请求参数{}, 错误信息{}", param);
            return R.failure("退款申请金额不能大于支付金额");
        }

        String notifyUrl;
        if (StringUtil.isEmpty(param.getAppServer())) {
            notifyUrl = String.format(appServer + "/api/payment/weixin/%s/notify/refund-success", paymentInfoBrief.getWeixinPaymentMerchantId());
        } else {
            notifyUrl = String.format(param.getAppServer() + "/api/payment/weixin/%s/notify/refund-success", paymentInfoBrief.getWeixinPaymentMerchantId());
        }

        String refundNo = param.getRefundNo();
        if (StringUtil.isEmpty(refundNo)) {
            refundNo = IdentityGenerator.generateRefundNo();
        }

        CreateRequest request = new CreateRequest();
        AmountReq amountReq = new AmountReq();
        amountReq.setRefund(ArithUtil.multiply(param.getRefundAmount(), new BigDecimal(100)).longValue());
        amountReq.setCurrency("CNY");
        amountReq.setTotal(ArithUtil.multiply(paymentInfoBrief.getPaymentAmount(), new BigDecimal(100)).longValue());
        request.setAmount(amountReq);
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(param.getOrderNo());
        request.setOutRefundNo(refundNo);
        request.setReason(param.getRefundReason());

        LocalDateTime now = LocalDateTime.now();
        try {
            Refund refund = refundService.create(request);
            log.info("退款申请请求结果:{}", JSON.toJSONString(refund));

            // 记录退款申请数据
            PaymentWeixinInfoPo paymentWeixinInfoPo = PaymentWeixinInfoPo.builder()
                    .weixinPaymentId(paymentInfoBrief.getWeixinPaymentId())
                    .refundApplied(WhetherDict.Yes.code)
                    .build();
            int update = paymentWeixinInfoMapper.updateById(paymentWeixinInfoPo);
            log.info("微信支付退款申请修改参数:{}, 响应结果:{}", JSON.toJSONString(paymentWeixinInfoPo), update);

            PaymentWeixinRefundPo refundPo = PaymentWeixinRefundPo.builder()
                    .weixinPaymentId(paymentInfoBrief.getWeixinPaymentId())
                    .refundNo(refundNo)
                    .refundReason(param.getRefundReason())
                    .refundApplyAmount(param.getRefundAmount())
                    .refundApplyTime(now)
                    .paymentNo(paymentInfoBrief.getPaymentNo())
                    .userId(paymentInfoBrief.getUserId())
                    .orderNo(paymentInfoBrief.getOrderNo())
                    .venueId(paymentInfoBrief.getVenueId())
                    .mctNo(paymentInfoBrief.getMctNo())
                    .createTime(now)
                    .build();
            int insert = refundMapper.insert(refundPo);
            log.info("微信支付退款明细参数新增:{}, 响应结果:{}", JSON.toJSONString(refundPo), insert);

            return R.ok("退款请求发送成功", refund);
        } catch (ServiceException e) {
            log.warn("微信支付退款申请失败请求参数:{}, 异常参数:{}", JSON.toJSONString(param), e);
            if (e.getHttpStatusCode() == HttpStatus.BAD_REQUEST.value()) {
                alarmSystemClient.alert("微信支付退款申请",
                        e.getMessage() + ", 请求参数:" + JSON.toJSONString(param), e);
                // 请求参数错误, 一般是开发期间异常
                return R.failure("退款申请失败, 请稍后再试, 或联系管理员操作(400)");
            } else if (e.getHttpStatusCode() == HttpStatus.FORBIDDEN.value()) {
                // 账户余额不足, 无法申请退款
                alarmSystemClient.alert("微信支付退款申请",
                        e.getMessage() + ", 请求参数:" + JSON.toJSONString(param), e);
                return R.failure("退款操作失败, 或联系管理员操作, 客服也会介入处理, 请稍后");
            } else {
                alarmSystemClient.alert("微信支付退款申请",
                        "退款申请异常,状态码:" + e.getHttpStatusCode() + ", 请求参数:" + JSON.toJSONString(param), e);
                throw BizException.error("退款申请失败, 请稍后再试, 或联系管理员操作(" + e.getHttpStatusCode() + ")");
            }
        } catch (Exception e) {
            log.error("微信支付退款申请异常:", e);
            throw BizException.error("申请退款失败, 请稍后再试");
        }
    }

    public R<RefundSuccessNotifyParam> refundSuccessVerify(RefundWeixinSuccessVerifyParam param) {
        log.info("收到微信退款执行成功通知, 通知参数:{}", JSON.toJSONString(param));
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(param.getWechatPayCertificateSerialNumber())
                .nonce(param.getNonce())
                .signature(param.getSignature())
                .timestamp(param.getTimestamp())
                .signType(param.getSignType())
                .body(param.getRequestBodyString())
                .build();

        JSONObject jsonObject = JSON.parseObject(param.getRequestBodyString());

        // 验签并解密报文
        Refund refund = notificationParser.parse(requestParam, Refund.class);
        log.info("微信退款执行成功, 解析报文：{}", refund);
        if (!"REFUND.SUCCESS".equals(jsonObject.getString("event_type"))) {
            log.warn("微信退款回调状态:{}, 放弃业务处理", refund.getStatus());
            return R.failure("微信退款回调状态非成功, 放弃业务处理");
        }

        PaymentWeixinInfoBrief brief = paymentWeixinInfoMapper.selectByNo(refund.getOutTradeNo());
        if (brief == null) {
            log.warn("微信支付单不存在退款回调状态:{}, 放弃业务处理", refund.getStatus());
            return R.failure("微信支付单不存在, 放弃业务处理");
        }

        // 微信支付表更新退款信息
        BigDecimal refundAmount = ArithUtil.divide(refund.getAmount().getRefund(), 100);
        PaymentWeixinInfoPo paymentWeixinInfoPo = PaymentWeixinInfoPo.builder()
                .weixinPaymentId(brief.getWeixinPaymentId())
                .refundTotalAmount(refundAmount)
                .refunded(WhetherDict.Yes.code)
                .build();
        int update = paymentWeixinInfoMapper.updateById(paymentWeixinInfoPo);
        log.info("微信退款回调更新参数:{}, 响应结果:{}", JSON.toJSONString(paymentWeixinInfoPo), update);

        PaymentWeixinRefundPo refundPo = PaymentWeixinRefundPo.builder()
                .refundNo(refund.getOutRefundNo())
                .notifyRefundAmount(refundAmount)
                .refundFinishTime(LocalDateTime.now())
                .refunded(WhetherDict.Yes.code)
                .outRefundNo(refund.getRefundId())
                .build();
        update = refundMapper.updateByRefundNo(refundPo);
        log.info("微信支付退款回调明细更新参数:{}, 响应结果:{}", JSON.toJSONString(refundPo), update);

        // 微信支付返回的 refundId 抽象成我们 refundNo
        RefundSuccessNotifyParam refundSuccessDto = RefundSuccessNotifyParam.builder()
                .orderNo(refund.getOutTradeNo())
                .paymentNo(refund.getTransactionId())
                .refundNo(refund.getOutRefundNo())
                .paymentRefundNo(refund.getRefundId())
                .refundAmount(refundAmount)
                .userReceivedAccount(refund.getUserReceivedAccount())
                .build();
        return R.ok(refundSuccessDto);
    }

}

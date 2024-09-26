package com.inbyte.component.common.payment.weixin.service;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.ArithUtil;
import com.inbyte.component.common.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.common.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinConfigMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.common.payment.weixin.model.*;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Amount;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.PrepayResponse;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信合作伙伴服务商支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
@Service
@Slf4j
public class PaymentWeixinPartnerService implements InitializingBean {

    @Value("${inbyte.app.server}")
    private String appServer;

    /**
     * 微信服务商商户号
     */
    public static String WEIXIN_PARTNER_MERCHANT_ID = "1672917102";
    /**
     * 微信服务商公众号APPID
     */
    public static String WEIXIN_PARTNER_APP_ID = "1672917102";
    /**
     * 商户API私钥路径
     */
    public static String privateKeyPath = "/Users/yourname/your/path/apiclient_key.pem";
    /**
     * 商户证书序列号
     */
    public static String merchantSerialNumber = "5157F09EFDC096DE15EBE81A47057A72********";
    /**
     * 商户APIV3密钥
     */
    public static String apiV3Key = "...";

    @Autowired
    private PaymentWeixinInfoMapper paymentWeixinInfoMapper;
    @Autowired
    private PaymentWeixinConfigMapper paymentWeixinConfigMapper;
    @Autowired
    private SystemAlarm alarmSystemClient;


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
        // 初始化商户配置
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(WEIXIN_PARTNER_APP_ID)
                        // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();

        // 初始化服务
        service = new JsapiService.Builder().config(config).build();
    }

    /**
     * 发起预支付订单
     *
     * @param prepayParam 预支付订单参数
     * @return 预支付订单响应
     */
    public R<PaymentWeixinPrepayDto> prepayOrder(PaymentWeixinPrepayParam prepayParam) {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(WEIXIN_PARTNER_MERCHANT_ID)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        // 构建service
        JsapiService service = new JsapiService.Builder().config(config).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(100);
        request.setAmount(amount);
        request.setSpAppid("wxa9d9651ae******");
        request.setSpMchid("190000****");
        request.setDescription("测试商品标题");
        request.setNotifyUrl("https://notify_url");
        request.setOutTradeNo("out_trade_no_001");
        // 调用下单方法，得到应答
        // 获取微信预支付ID
        try {
            PrepayResponse response = service.prepay(request);
            return requestPayment(prepayParam, response.getPrepayId());
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
    private R<PaymentWeixinPrepayDto> requestPayment(PaymentWeixinPrepayParam paymentWeixinPrepayParam, String prepayId) {
        // 创建微信支付信息数据
        PaymentWeixinInfoPo paymentWeixinInfoPo = PaymentWeixinInfoPo.builder()
                .userId(paymentWeixinPrepayParam.getUserId())
                .orderNo(paymentWeixinPrepayParam.getOrderNo())
                .orderBrief(paymentWeixinPrepayParam.getOrderBrief())
                .orderType(paymentWeixinPrepayParam.getOrderType())
                .mainPhoto(paymentWeixinPrepayParam.getMainPhoto())
                .venueId(paymentWeixinPrepayParam.getVenueId())
                .mctNo(paymentWeixinPrepayParam.getMctNo())
                .appId(paymentWeixinPrepayParam.getAppId())
                .paymentAmount(paymentWeixinPrepayParam.getPaymentAmount())
//                .weixinPaymentMerchantId(weixinPaymentId)
                .paid(Whether.Yes)
                .prepayId(prepayId)
                .createTime(LocalDateTime.now())
                .build();
        int insert = paymentWeixinInfoMapper.insertSelective(paymentWeixinInfoPo);
        log.info("微信支付数据创建参数:{}, 响应结果:{}", JSON.toJSONString(paymentWeixinInfoPo), insert);


//        Signer signer = service.createSigner();
//        long timestamp = Instant.now().getEpochSecond();
//        String nonceStr = NonceUtil.createNonce(32);
//        String packageVal = "prepay_id=" + prepayId;
//        String message = paymentWeixinPrepayParam.getAppId() + "\n" + timestamp + "\n" + nonceStr + "\n" + packageVal + "\n";
//        String sign = signer.sign(message).getSign();

        PaymentWeixinPrepayDto requestPayment = PaymentWeixinPrepayDto.builder()
//                .timeStamp(String.valueOf(timestamp))
//                .nonceStr(nonceStr)
//                .packageVal(packageVal)
//                .paySign(sign)
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

//        NotificationParser parser = new NotificationParser(getConfig(PARTNER_ID));
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

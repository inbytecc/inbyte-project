package com.inbyte.component.app.order.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.WebUtil;
import com.inbyte.component.app.order.service.OrderServiceFactory;
import com.inbyte.component.app.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.app.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.app.payment.weixin.model.PaymentWeixinResult;
import com.inbyte.component.app.payment.weixin.model.PaymentWeixinSuccessVerifyParam;
import com.inbyte.component.app.payment.weixin.model.RefundWeixinSuccessVerifyParam;
import com.inbyte.component.app.payment.weixin.service.PaymentWeixinService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信支付回调
 *
 * @author: chenjw
 * @data: 2023/1/9
 */
@RestController
@RequestMapping("payment/weixin/{weixinMerchantId}")
public class PaymentWeixinController implements InitializingBean {

    @Autowired
    private PaymentWeixinService paymentWeixinService;
    @Autowired
    private OrderServiceFactory orderServiceFactory;

    private PaymentWeixinResult weixinSuccessResult;
    private PaymentWeixinResult weixinFailureResult;

    @Override
    public void afterPropertiesSet() {
        weixinSuccessResult = new PaymentWeixinResult("SUCCESS", "成功");
        weixinFailureResult = new PaymentWeixinResult("FAIL", "失败");
    }

    /**
     * 支付回调
     *
     * @param request
     * @return
     */
    @PostMapping("notify/payment-success")
    public PaymentWeixinResult paymentSuccessNotify(@PathVariable("weixinMerchantId") String weixinMerchantId,
                                                    HttpServletRequest request) {
        // 从请求头里获取相关信息, 用于验证签名
        // 时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        // 随机字符串
        String nonce = request.getHeader("Wechatpay-Nonce");
        // 签名
        String signature = request.getHeader("Wechatpay-Signature");
        // 签名类型
        String signType = request.getHeader("Wechatpay-Signature-Type");
        // 证书序列号
        String wechatPayCertificateSerialNumber = request.getHeader("Wechatpay-Serial");

        PaymentWeixinSuccessVerifyParam build = PaymentWeixinSuccessVerifyParam.builder()
                .weixinPaymentMerchantId(weixinMerchantId)
                .timestamp(timestamp)
                .nonce(nonce)
                .signature(signature)
                .signType(signType)
                .wechatPayCertificateSerialNumber(wechatPayCertificateSerialNumber)
                .requestBodyString(WebUtil.getRequestBodyString(request))
                .build();
        R<PaymentSuccessNotifyParam> paymentSuccessDtoR = paymentWeixinService.paymentSuccessVerify(build);
        if (paymentSuccessDtoR.failed()) {
            return weixinFailureResult;
        }
        PaymentSuccessNotifyParam paymentSuccessNotifyParam = paymentSuccessDtoR.getData();
        try {
            R r = orderServiceFactory.getServiceByOrderNo(paymentSuccessNotifyParam.getOrderNo()).paymentSuccessNotify(paymentSuccessNotifyParam);
            if (r.failed()) {
                return weixinFailureResult;
            }
            return weixinSuccessResult;
        } catch (Exception e) {
            return weixinFailureResult;
        }
    }

    /**
     * 退款回调
     *
     * @param request
     * @return
     */
    @PostMapping("notify/refund-success")
    public PaymentWeixinResult refundSuccessNotify(@PathVariable("weixinMerchantId") String weixinMerchantId, HttpServletRequest request) {
        // 从请求头里获取相关信息, 用于验证签名
        // 时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        // 随机字符串
        String nonce = request.getHeader("Wechatpay-Nonce");
        // 签名
        String signature = request.getHeader("Wechatpay-Signature");
        // 签名类型
        String signType = request.getHeader("Wechatpay-Signature-Type");
        // 证书序列号
        String wechatPayCertificateSerialNumber = request.getHeader("Wechatpay-Serial");
        RefundWeixinSuccessVerifyParam build = RefundWeixinSuccessVerifyParam.builder()
                .weixinPaymentMerchantId(weixinMerchantId)
                .timestamp(timestamp)
                .nonce(nonce)
                .signature(signature)
                .signType(signType)
                .wechatPayCertificateSerialNumber(wechatPayCertificateSerialNumber)
                .requestBodyString(WebUtil.getRequestBodyString(request))
                .build();
        R<RefundSuccessNotifyParam> refundWeixinSuccessR = paymentWeixinService.refundSuccessVerify(build);
        if (refundWeixinSuccessR.failed()) {
            return weixinFailureResult;
        }
        RefundSuccessNotifyParam data = refundWeixinSuccessR.getData();
        R r = orderServiceFactory.getServiceByOrderNo(data.getOrderNo())
                .refundSuccessNotify(data);
        if (r.failed()) {
            return weixinFailureResult;
        }
        return weixinSuccessResult;
    }

}

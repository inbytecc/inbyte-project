package com.inbyte.component.app.order.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.order.model.PaymentAlipayPrepayDto;
import com.inbyte.component.app.order.model.refund.RefundApplyParam;
import com.inbyte.component.app.payment.weixin.model.PaymentWeixinPrepayDto;
import com.inbyte.component.app.user.framework.aspect.Logged;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderControllerCommonApi {

    /**
     * 微信小程序预支付
     * <p>
     * 微信小程序预支付, 获取小程序调起微信支付所需的参数</br>
     * 要求: 在小程序登录过账号
     *
     * @param orderNo
     * @return
     */
    @Logged
    @GetMapping("{orderNo}/payment/weixin/prepay")
    R<PaymentWeixinPrepayDto> weixinPrepay(@PathVariable("orderNo") String orderNo);

    /**
     * 查询微信是否支付成功
     * <p>
     * 接口返回200: 支付成功
     * 接口返回400: 提示"正在确认支付结果", 同时轮询请求此接口, 轮询频率1s\2s\3s\5s, 此后统一10s一次
     *
     * @return
     */
    @Logged
    @GetMapping("{orderNo}/payment/weixin/status")
    R queryPaymentStatus(@PathVariable("orderNo") String orderNo);


    /**
     * 支付宝小程序预支付
     * <p>
     * 微信小程序预支付, 获取小程序调起微信支付所需的参数</br>
     * 要求: 在小程序登录过账号
     *
     * @param orderNo
     * @return
     */
    @Logged
    @GetMapping("{orderNo}/payment/alipay/prepay")
    R<PaymentAlipayPrepayDto> alipayPrepay(@PathVariable("orderNo") String orderNo);

    /**
     * 申请退款
     *
     * @param refundApplyParam
     * @return
     */
    @PostMapping("refund-apply")
    R refundApply(@RequestBody @Valid RefundApplyParam refundApplyParam);

}

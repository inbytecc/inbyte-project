//package com.inbyte.component.app.payment.weixin.client;
//
//import com.inbyte.component.app.payment.weixin.model.*;
//import com.inbyte.commons.model.dto.R;
//import com.inbyte.component.app.payment.weixin.service.PaymentWeixinService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 微信支付
// *
// * @author chenjw
// * @date 2023-10-26 13:17:52
// **/
//@Slf4j
//@RestController
//public class PaymentWeixinClient {
//
//    @Autowired
//    private PaymentWeixinService paymentWeixinService;
//
//    @PostMapping("create")
//    public R<PaymentWeixinPrepayDto> prepayOrder(@RequestBody PaymentWeixinPrepayParam paymentWeixinPrepayParam) {
//        return paymentWeixinService.prepayOrder(paymentWeixinPrepayParam);
//    }
//
//    /**
//     * 支付成功回调
//     *
//     * @param param
//     * @return
//     */
//    @PostMapping("payment-success-verify")
//    public R<PaymentSuccessDto> paymentSuccessVerify(@RequestBody PaymentWeixinSuccessVerifyParam param) {
//        return paymentWeixinService.paymentSuccessVerify(param);
//    }
//
//    /**
//     * 支付成功回调
//     *
//     * @param param
//     * @return
//     */
//    @PostMapping("refund-success-verify")
//    public R<RefundSuccessDto> refundSuccessVerify(@RequestBody RefundWeixinSuccessVerifyParam param) {
//        return paymentWeixinService.refundSuccessVerify(param);
//    }
//
////    /**
////     * 取消支付
////     *
////     * @return
////     */
////    @PostMapping("{orderNo}/close")
////    public Result<PaymentSuccessDto> close(@PathVariable String orderNo) {
////        return paymentWeixinService.close(orderNo);
////    }
//
////    /**
////     * 发起退款请求
////     *
////     * @param param
////     * @return
////     */
////    @PostMapping("refund-apply")
////    public Result refundApply(@RequestBody AlipayRefundApplyParam param) {
////        try {
////            return paymentWeixinService.refundApply(param);
////        } catch (AlipayApiException e) {
////            return Result.failure("微信支付申请退款失败, 请稍后再试下");
////        }
////    }
//
//}

package com.inbyte.component.app.payment.common.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.commons.model.enums.PaymentTypeEnum;
import com.inbyte.component.app.payment.weixin.model.RefundCommonApplyParam;
import com.inbyte.component.app.payment.weixin.service.PaymentWeixinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 通用支付服务
 *
 * @author chenjw
 * @date 20231007
 */
@Service
@Slf4j
public class PaymentCommonService {

//    @Autowired
//    private PaymentALI_PAYService paymentALI_PAYService;
    @Autowired
    private PaymentWeixinService paymentWeixinService;

    /**
     * 查询支付状态
     *
     * @param orderNo
     * @param paymentType
     * @return
     */
    public R<PaymentSuccessNotifyParam> queryPaymentStatus(@PathVariable("orderNo") String orderNo,
                                                           @PathVariable("paymentType") PaymentTypeEnum paymentType) {
        if (PaymentTypeEnum.ALI_PAY == paymentType) {
//            return paymentALI_PAYService.queryPaymentStatus(orderNo);
        } else if (PaymentTypeEnum.WEIXIN_PAY == paymentType) {
            return paymentWeixinService.queryPaymentStatus(orderNo);
        }
        return R.failure("该订单未支付");
    }

    /**
     * 关闭交易
     *
     * @param orderNo
     * @param paymentType
     * @return
     */
    public R close(String orderNo, PaymentTypeEnum paymentType) {
        if (PaymentTypeEnum.ALI_PAY == paymentType) {
//            return paymentALI_PAYService.close(orderNo);
        } else if (PaymentTypeEnum.WEIXIN_PAY == paymentType) {
            return paymentWeixinService.close(orderNo);
        }
        return R.failure("该订单未支付");
    }

    /**
     * 申请退款
     *
     * @param param
     * @return
     */
    public R refundApply(RefundCommonApplyParam param) {
        if (PaymentTypeEnum.ALI_PAY == param.getPaymentType()) {
//            return paymentALI_PAYService.refundApply(param);
        } else if (PaymentTypeEnum.WEIXIN_PAY == param.getPaymentType()) {
            return paymentWeixinService.refundApply(param);
        }
        return R.failure("该订单未支付");
    }
}

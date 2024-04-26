package com.inbyte.component.app.payment.weixin.service;

import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.app.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.app.payment.weixin.dao.PaymentWeixinConfigMapper;
import com.inbyte.component.app.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.app.payment.weixin.model.*;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
@Service
@Slf4j
public class PaymentWeixinService {

    @Autowired
    private PaymentWeixinInfoMapper paymentWeixinInfoMapper;
    @Autowired
    private PaymentWeixinConfigMapper paymentWeixinConfigMapper;

    @Autowired
    private PaymentWeixinMerchantService paymentWeixinMerchantService;
    @Autowired
    private PaymentWeixinPartnerService paymentWeixinPartnerService;

    /**
     * 发起预付单
     * <p>
     * 微信预付单接口文档：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml
     *
     * @param prepaidOrderParam
     * @return
     */
    public R<PaymentWeixinPrepayDto> prepayOrder(PaymentWeixinPrepayParam prepaidOrderParam) {
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(prepaidOrderParam.getWeixinPaymentMerchantId());
        if (paymentWeixinConfigPo == null) {
            return R.failure("商户未配置收款信息, 暂不支持在线支付");
        }

        // 服务商支付模式
        if (paymentWeixinConfigPo.getPartnerPay() == Whether.Yes) {
            return paymentWeixinPartnerService.prepayOrder(prepaidOrderParam);
        }
        // 商户支付模式
        return paymentWeixinMerchantService.prepayOrder(prepaidOrderParam);
    }

    public R close(String orderNo) {
        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(orderNo);
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());
        // 服务商支付模式
        if (paymentWeixinConfigPo.getPartnerPay() == Whether.Yes) {
            return paymentWeixinPartnerService.close(orderNo);
        }
        // 商户支付模式
        return paymentWeixinMerchantService.close(orderNo);
    }


    /**
     * 查询支付状态
     *
     * @param orderNo
     * @return
     */
    public R<PaymentSuccessNotifyParam> queryPaymentStatus(String orderNo) {
        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(orderNo);
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());
        // 服务商支付模式
        if (paymentWeixinConfigPo.getPartnerPay() == Whether.Yes) {
            return paymentWeixinPartnerService.queryPaymentStatus(orderNo);
        }
        // 商户支付模式
        return paymentWeixinMerchantService.queryPaymentStatus(orderNo);
    }

    /**
     * 退款申请
     * 微信支付退款 API 接口文档： https://pay.weixin.qq.com/wiki/doc/apiv3_partner/open/pay/chapter2_4.shtml
     *
     * @param param
     * @return
     */
    public R<Refund> refundApply(RefundCommonApplyParam param) {
        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(param.getOrderNo());
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());
        // 服务商支付模式
        if (paymentWeixinConfigPo.getPartnerPay() == Whether.Yes) {
            return paymentWeixinPartnerService.refundApply(param);
        }
        // 商户支付模式
        return paymentWeixinMerchantService.refundApply(param);
    }

//    @Override
//    public Result<Refund> queryRefundOrder(String refundNo) {
//        String weixinMerchantId = null;
//        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
//        request.setOutRefundNo(refundNo);
//        request.setSubMchid(weixinMerchantId);
//        // 调用接口
//        RefundService service = new RefundService.Builder().config(getConfig(weixinMerchantId)).build();
//        Refund refund = service.queryByOutRefundNo(request);
//        log.info("退款查询结果:{}", refund);
//        return Result.success(refund);
//    }

    /**
     * 微信支付异步通知
     * 对微信的支付回调进行验签, 以及处理相关的业务逻辑
     *
     * @ref https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_1.shtml
     **/
    public R<PaymentSuccessNotifyParam> paymentSuccessVerify(PaymentWeixinSuccessVerifyParam param) {
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(param.getWeixinPaymentMerchantId());
        // 服务商支付模式
        if (paymentWeixinConfigPo.getPartnerPay() == Whether.Yes) {
            return paymentWeixinPartnerService.paymentSuccessVerify(param);
        }
        // 商户支付模式
        return paymentWeixinMerchantService.paymentSuccessVerify(param);
    }

    public R<RefundSuccessNotifyParam> refundSuccessVerify(RefundWeixinSuccessVerifyParam param) {
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(param.getWeixinPaymentMerchantId());
        // 服务商支付模式
        if (paymentWeixinConfigPo.getPartnerPay() == Whether.Yes) {
            return paymentWeixinPartnerService.refundSuccessVerify(param);
        }
        // 商户支付模式
        return paymentWeixinMerchantService.refundSuccessVerify(param);
    }

}

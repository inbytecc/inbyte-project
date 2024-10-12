package com.inbyte.component.common.payment.weixin.service;

import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.enums.OrderTypeEnum;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.common.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.common.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.common.payment.weixin.dao.PaymentVenueMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinConfigMapper;
import com.inbyte.component.common.payment.weixin.dao.PaymentWeixinInfoMapper;
import com.inbyte.component.common.payment.weixin.model.*;
import com.inbyte.component.common.payment.weixin.service.impl.PaymentWeixinMerchantServiceImpl;
import com.inbyte.component.common.payment.weixin.service.impl.PaymentWeixinPartnerServiceImpl;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private PaymentWeixinMerchantServiceImpl paymentWeixinMerchantServiceImpl;
    @Autowired
    private PaymentWeixinPartnerServiceImpl paymentWeixinPartnerServiceImpl;

    @Autowired
    private PaymentVenueMapper paymentVenueMapper;

    /**
     * 发起预付单
     * <p>
     * 微信预付单接口文档：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml
     *
     * @param prepaidOrderParam
     * @return
     */
    public R<PaymentWeixinPrepayDto> prepayOrder(PaymentWeixinPrepayParam prepaidOrderParam) {
        log.info("微信支付签名:{}", JSON.toJSONString(prepaidOrderParam));
        PaymentWeixinConfigPo weixinPaymentConfig = getWeixinPaymentConfig(prepaidOrderParam.getVenueId(), prepaidOrderParam.getOrderType());

        return getPaymentService(weixinPaymentConfig)
                .prepayOrder(prepaidOrderParam, weixinPaymentConfig.getWeixinPaymentMerchantId());
    }

    public R close(String orderNo) {
        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(orderNo);
        if (paymentWeixinInfoBrief == null) {
            return R.failure("查不到该【" + orderNo + "】订单的支付记录");
        }
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());

        return getPaymentService(paymentWeixinConfigPo).close(orderNo);
    }


    /**
     * 查询支付状态
     *
     * @param orderNo
     * @return
     */
    public R<PaymentSuccessNotifyParam> queryPaymentStatus(String orderNo) {
        PaymentWeixinInfoBrief paymentWeixinInfoBrief = paymentWeixinInfoMapper.selectByNo(orderNo);
        if (paymentWeixinInfoBrief == null) {
            return R.failure("查不到该【" + orderNo + "】订单的支付记录");
        }
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());

        return getPaymentService(paymentWeixinConfigPo).queryPaymentStatus(orderNo);
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
        if (paymentWeixinInfoBrief == null) {
            return R.failure("查不到该【" + param.getOrderNo() + "】订单的支付记录");
        }
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentWeixinInfoBrief.getWeixinPaymentMerchantId());

        return getPaymentService(paymentWeixinConfigPo).refundApply(param);

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

        return getPaymentService(paymentWeixinConfigPo).paymentSuccessVerify(param);
    }

    public R<RefundSuccessNotifyParam> refundSuccessVerify(RefundWeixinSuccessVerifyParam param) {
        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(param.getWeixinPaymentMerchantId());

        return getPaymentService(paymentWeixinConfigPo).refundSuccessVerify(param);
    }

    /**
     * 获取微信支付配置信息
     *
     * @param venueId
     * @param orderType
     * @return
     */
    private PaymentWeixinConfigPo getWeixinPaymentConfig(String venueId, OrderTypeEnum orderType) {
        PaymentVenuePo paymentVenuePo = paymentVenueMapper.selectById(venueId);
        if (paymentVenuePo == null) {
            throw InbyteException.error("该商户未配置微信支付");
        }

        String paymentMctId = paymentVenuePo.getWeixinPaymentId();
        Optional<PaymentConfigItem> paymentConfigItem = paymentVenuePo.getAliOrderPaymentConfig().stream()
                .filter(paymentConfig -> paymentConfig.getOrderType().equals(orderType))
                .findFirst();
        if (paymentConfigItem.isPresent()) {
            paymentMctId = paymentConfigItem.get().getPaymentMctId();
        }

        if (StringUtil.isEmpty(paymentMctId)) {
            throw InbyteException.error("该商户未配置微信支付");
        }

        PaymentWeixinConfigPo paymentWeixinConfigPo = paymentWeixinConfigMapper.selectById(paymentMctId);
        if (paymentWeixinConfigPo == null) {
            throw InbyteException.failure("商户未配置收款信息, 暂不支持在线支付");
        }

        return paymentWeixinConfigPo;
    }

    private PaymentWeixinServiceApi getPaymentService(PaymentWeixinConfigPo paymentWeixinConfigPo) {
        return paymentWeixinConfigPo.getPartnerPay() == Whether.Yes ? paymentWeixinPartnerServiceImpl : paymentWeixinMerchantServiceImpl;
    }
}

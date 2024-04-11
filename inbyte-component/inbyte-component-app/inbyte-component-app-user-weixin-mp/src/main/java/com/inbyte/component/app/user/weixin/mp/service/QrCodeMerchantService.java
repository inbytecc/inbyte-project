package com.inbyte.component.app.user.weixin.mp.service;

import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpPo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrCodePurchaseEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrRegisterMerchantParam;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrcodeMerchantPo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;

import java.math.BigDecimal;

/**
 * 二维码事件
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
public interface QrCodeMerchantService {

    QrcodeMerchantPo detail(Integer qcid);

    /**
     * 绑定关系
     *
     * @return
     */
    void buildRelation(Integer qcid, Integer eid, AppTypeEnum etp);

    /**
     * 访问事件
     *
     * @param scanEventNotify
     * @return
     */
    void viewed(ScanEventNotify scanEventNotify);

    /**
     * 用户注册事件
     *
     * @param qrRegisterMerchantParam
     * @return
     */
    void registered(QrRegisterMerchantParam qrRegisterMerchantParam);

    /**
     * 订单下单事件
     *
     * @param purchaseEventNotify
     * @return
     */
    void purchased(QrCodePurchaseEventNotify purchaseEventNotify, UserWeixinMpPo userWeixinMpPo);

    void newClue(Integer eid, AppTypeEnum etp);

    void syncLocation(Integer qcid, Integer eid, AppTypeEnum etp, BigDecimal longitude, BigDecimal latitude);

}
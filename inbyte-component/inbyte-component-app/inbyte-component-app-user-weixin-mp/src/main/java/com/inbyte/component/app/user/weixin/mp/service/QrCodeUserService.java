package com.inbyte.component.app.user.weixin.mp.service;

import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpPo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrCodePurchaseEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;

/**
 * 二维码事件
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
public interface QrCodeUserService {

    /**
     * 绑定关系
     *
     * @return
     */
    void buildRelation(Integer registerEid, Integer recommendEid);

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
     * @return
     */
    void registered(Integer registerEid);

    /**
     * 订单下单事件
     *
     * @param purchaseEventNotify
     * @return
     */
    void purchased(QrCodePurchaseEventNotify purchaseEventNotify, UserWeixinMpPo userWeixinMpPo);

    void newClue(Integer eid, AppTypeEnum etp);
}
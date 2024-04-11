package com.inbyte.component.app.user.weixin.mp.service;

import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.BuildRelationParam;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrCodePurchaseEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ShareDto;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;

/**
 * 分享服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
public interface QrCodeService {

    /**
     * 生成分享
     *
     * @param param
     * @return Result
     **/
    R<ShareDto> shareDirectly(BasePath param);

    /**
     * 创建分享二维码
     *
     * @param param
     * @return
     */
    R qrCode(BasePath param);

    /**
     * 访问事件
     *
     * @param buildRelationParam
     * @return
     */
    void buildRelation(BuildRelationParam buildRelationParam);

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
    void registered(Integer eid, AppTypeEnum etp);

    /**
     * 订单下单事件
     *
     * @param purchaseEventNotify
     * @return
     */
    void purchased(QrCodePurchaseEventNotify purchaseEventNotify);

//    /**
//     * 已预约
//     *
//     * @param eid
//     */
//    void appointed(Integer eid, AppTypeEnum etp);

    @Async
    void syncLocation(Integer eid, AppTypeEnum etp, BigDecimal longitude, BigDecimal latitude);

    /**
     * 注册推荐描述
     *
     * @param qctp
     * @param qcid
     * @param recommentEid
     * @return
     */
    String getRegisterRemark(Integer qctp, Integer qcid, Integer recommentEid);
}
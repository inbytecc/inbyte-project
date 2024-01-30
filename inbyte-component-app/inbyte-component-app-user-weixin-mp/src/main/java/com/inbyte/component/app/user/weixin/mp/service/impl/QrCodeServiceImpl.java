package com.inbyte.component.app.user.weixin.mp.service.impl;

import com.inbyte.component.app.user.dict.UserSourceTypeDict;
import com.inbyte.component.app.user.weixin.mp.SceneUtil;
import com.inbyte.component.app.user.weixin.mp.dao.UserWeixinMpMapper;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinDto;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpPo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.*;
import com.inbyte.component.app.user.weixin.mp.service.QrCodeMerchantService;
import com.inbyte.component.app.user.weixin.mp.service.QrCodeService;
import com.inbyte.component.app.user.weixin.mp.service.QrCodeUserService;
import com.inbyte.commons.model.dict.AppTypeDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.framework.AppUtil;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.util.weixin.mp.client.WxMpQrCodeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 分享服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private QrCodeMerchantService qrCodeMerchantService;
    @Autowired
    private QrCodeUserService qrCodeUserService;
    @Autowired
    private WxMpQrCodeClient wxMpQrCodeClient;
    @Autowired
    private UserWeixinMpMapper userWeixinMpMapper;

    /**
     * 是否商家分享
     *
     * @param t
     * @return
     */
    private boolean isMerchantShare(Integer t) {
        return t != null && t == UserSourceTypeDict.Merchant_Share.code;
    }

    /**
     * 是否商家分享
     *
     * @param t
     * @return
     */
    private boolean isUserShare(Integer t) {
        return t != null && t == UserSourceTypeDict.User_Share.code;
    }

    /**
     * 直接分享
     *
     * @param param
     * @return
     */
    @Override
    public R<ShareDto> shareDirectly(BasePath param) {
        ShareDto shareDto = new ShareDto();
        shareDto.setScene(SceneUtil.getUserShareScene(SessionUtil.getEid(), param.getPathParam()));
        return R.success("创建成功", shareDto);
    }


    /**
     * 获取小程序码, 适用于需要的码数量极多的业务场景。
     * 通过该接口生成的小程序码, 永久有效, 数量暂无限制。 更多用法详见 获取二维码。
     * 接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R qrCode(BasePath param) {
        String scene = SceneUtil.getUserShareScene(SessionUtil.getEid(), param.getPathParam());
        return wxMpQrCodeClient.qrCodeBase64(AppUtil.getAppId(), param.getPath(), scene, 430);
    }

    @Async
    @Override
    public void viewed(ScanEventNotify scanEventNotify) {
        if (isMerchantShare(scanEventNotify.getT())) {
            qrCodeMerchantService.viewed(scanEventNotify);
        } else if (isUserShare(scanEventNotify.getT())) {
            qrCodeUserService.viewed(scanEventNotify);
        }
    }

    @Async
    @Override
    public void registered(Integer eid, Integer etp) {
        UserWeixinMpPo userWeixinMpPo = userWeixinMpMapper.selectById(eid);
        if (userWeixinMpPo == null || userWeixinMpPo.getRegisterType() == null) {
            return;
        }
        if (isMerchantShare(userWeixinMpPo.getRegisterType())) {
            QrRegisterMerchantParam qrRegisterMerchantParam = QrRegisterMerchantParam.builder()
                    .eid(eid)
                    .etp(etp)
                    .q(userWeixinMpPo.getQcid())
                    .t(userWeixinMpPo.getRegisterType())
                    .build();
            qrCodeMerchantService.registered(qrRegisterMerchantParam);
        } else if (isUserShare(userWeixinMpPo.getRegisterType())) {
            qrCodeUserService.registered(eid);
        }
    }

    @Async
    @Override
    public void purchased(QrCodePurchaseEventNotify purchaseEventNotify) {
        UserWeixinMpPo userWeixinMpPo = userWeixinMpMapper.selectById(purchaseEventNotify.getEid());
        if (userWeixinMpPo == null || userWeixinMpPo.getRegisterType() == null) {
            return;
        }
        if (isMerchantShare(userWeixinMpPo.getRegisterType())) {
            qrCodeMerchantService.purchased(purchaseEventNotify, userWeixinMpPo);
        } else if (isUserShare(userWeixinMpPo.getRegisterType())) {
            qrCodeUserService.purchased(purchaseEventNotify, userWeixinMpPo);
        }
    }

    @Async
    @Override
    public void appointed(Integer eid, Integer etp) {
        if (isMerchantShare(etp)) {
            qrCodeMerchantService.newClue(eid, etp);
        } else if (isUserShare(etp)) {
            qrCodeUserService.newClue(eid, etp);
        }
    }

    @Override
    public void buildRelation(BuildRelationParam buildRelationParam) {
        if (isMerchantShare(buildRelationParam.getQctp())) {
            qrCodeMerchantService.buildRelation(buildRelationParam.getQcid(), buildRelationParam.getEid(), buildRelationParam.getEtp());
        } else if (isUserShare(buildRelationParam.getQctp())) {
            qrCodeUserService.buildRelation(buildRelationParam.getEid(), buildRelationParam.getRecommendEid());
        }
    }

    @Async
    @Override
    public void syncLocation(Integer eid, Integer etp, BigDecimal longitude, BigDecimal latitude) {
        if (etp == AppTypeDict.Weixin_MiniProgram.code) {
            UserWeixinMpPo userWeixinMpPo = userWeixinMpMapper.selectById(eid);
            if (isMerchantShare(userWeixinMpPo.getRegisterType())) {
                qrCodeMerchantService.syncLocation(userWeixinMpPo.getQcid(), eid, etp, longitude, latitude);
            } else {
                // 暂不同步微信推荐用户位置
            }
        }
    }

    @Override
    public String getRegisterRemark(Integer qctp, Integer qcid, Integer recommendEid) {
        if (isMerchantShare(qctp)) {
            QrcodeMerchantPo detail = qrCodeMerchantService.detail(qcid);
            if (detail != null) {
                return detail.getName();
            }
        } else if (isUserShare(qctp)) {
            UserWeixinDto info = userWeixinMpMapper.info(recommendEid);
            if (info != null) {
                return info.getNickName();
            }
        }
        return null;
    }
}
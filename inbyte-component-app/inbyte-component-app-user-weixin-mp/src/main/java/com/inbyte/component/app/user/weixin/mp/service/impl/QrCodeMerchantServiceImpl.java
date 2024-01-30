package com.inbyte.component.app.user.weixin.mp.service.impl;

import com.inbyte.component.app.user.weixin.mp.dao.QrcodeMerchantMapper;
import com.inbyte.component.app.user.weixin.mp.dao.QrcodeMerchantUserMapper;
import com.inbyte.component.app.user.weixin.mp.dao.UserWeixinMpMapper;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpPo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrCodePurchaseEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrRegisterMerchantParam;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrcodeMerchantPo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.merchant.user.QrcodeMerchantUserPo;
import com.inbyte.component.app.user.weixin.mp.service.QrCodeMerchantService;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.WhetherDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商家分享二维码服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeMerchantServiceImpl implements QrCodeMerchantService {

    @Autowired
    private QrcodeMerchantMapper qrcodeMerchantMapper;
    @Autowired
    private QrcodeMerchantUserMapper qrcodeMerchantUserMapper;
    @Autowired
    private UserWeixinMpMapper userWeixinMpMapper;

    @Override
    public QrcodeMerchantPo detail(Integer qcid) {
        if (qcid == null) {
            return null;
        }
        return qrcodeMerchantMapper.selectById(qcid);
    }

    @Override
    public void buildRelation(Integer qcid, Integer eid, Integer etp) {
        log.info("建立用户二维码关系 qcid:{}, eid:{}, etp:{}", qcid, eid, etp);
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(qcid);
        if (detail == null) {
            log.warn("扫码的二维码不存在, 建立关系失败");
            return;
        }

        UserWeixinMpPo externalUser = userWeixinMpMapper.selectById(eid);
        if (externalUser == null) {
            log.info("建立用户二维码关失败, 用户信息不存在, qcid:{}, eid:{}, etp:{}", qcid, eid, etp);
            return;
        }

        LambdaQueryWrapper<QrcodeMerchantUserPo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(QrcodeMerchantUserPo::getQcid, qcid);
        queryWrapper.eq(QrcodeMerchantUserPo::getEid, eid);
        queryWrapper.eq(QrcodeMerchantUserPo::getEtp, etp);
        QrcodeMerchantUserPo merchantUserPo = qrcodeMerchantUserMapper.selectOne(queryWrapper);
        if (merchantUserPo != null) {
            return;
        }

        QrcodeMerchantUserPo qrcodeMerchantUserPo = QrcodeMerchantUserPo.builder()
                .qcid(qcid)
                .eid(eid)
                .etp(etp)
                .longitude(externalUser.getLongitude())
                .latitude(externalUser.getLatitude())
                .createTime(LocalDateTime.now())
                .build();
        int i = qrcodeMerchantUserMapper.insertSelective(qrcodeMerchantUserPo);
        if (i > 0) {
            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper();
            updateWrapper.eq(QrcodeMerchantPo::getQcid, qcid);
            updateWrapper.setSql("relationCount = relationCount + 1");
            qrcodeMerchantMapper.update(null, updateWrapper);
        }
        log.info("关联用户商户二维码关系:{}, {}, {}", qcid, eid, etp);
    }

    @Override
    public void viewed(ScanEventNotify scanEventNotify) {
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(scanEventNotify.getQ());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(QrcodeMerchantPo::getQcid, scanEventNotify.getQ());
        updateWrapper.setSql("viewCount = viewCount + 1");
        qrcodeMerchantMapper.update(null, updateWrapper);

        log.info("用户扫描商家码, 扫码信息:{}, 二维码:{}",
                JSON.toJSONString(scanEventNotify),
                JSON.toJSONString(detail));

        this.buildRelation(scanEventNotify.getQ(), scanEventNotify.getEid(), scanEventNotify.getEtp());
    }


    @Override
    public void registered(QrRegisterMerchantParam qrRegisterMerchantParam) {
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(qrRegisterMerchantParam.getQ());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(QrcodeMerchantPo::getQcid, qrRegisterMerchantParam.getQ());
        updateWrapper.setSql("registerCount = registerCount + 1");
        qrcodeMerchantMapper.update(null, updateWrapper);

        LambdaUpdateWrapper<QrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper();
        qmUpdateWrapper.eq(QrcodeMerchantUserPo::getQcid, qrRegisterMerchantParam.getQ());
        qmUpdateWrapper.eq(QrcodeMerchantUserPo::getEid, qrRegisterMerchantParam.getEid());
        qmUpdateWrapper.eq(QrcodeMerchantUserPo::getEtp, qrRegisterMerchantParam.getEtp());
        qmUpdateWrapper.set(QrcodeMerchantUserPo::getRegistered, WhetherDict.Yes.code);
        qrcodeMerchantUserMapper.update(null, qmUpdateWrapper);

        log.info("商家码用户注册事件:{}, 二维码:{}",
                JSON.toJSONString(qrRegisterMerchantParam),
                JSON.toJSONString(detail));
    }

    @Override
    public void purchased(QrCodePurchaseEventNotify purchaseEventNotify, UserWeixinMpPo userWeixinMpPo) {
        if (userWeixinMpPo.getQcid() == null) {
            return;
        }
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(userWeixinMpPo.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }
        LambdaUpdateWrapper<QrcodeMerchantUserPo> updateWrapper1 = new LambdaUpdateWrapper();
        updateWrapper1.eq(QrcodeMerchantUserPo::getQcid, userWeixinMpPo.getQcid());
        updateWrapper1.eq(QrcodeMerchantUserPo::getEid, userWeixinMpPo.getEid());
        updateWrapper1.set(QrcodeMerchantUserPo::getMadeDeal, WhetherDict.Yes.code);
        updateWrapper1.setSql("orderCount = orderCount + 1");
        updateWrapper1.setSql("tradeAmount = tradeAmount + " + purchaseEventNotify.getOrderAmount());

        int update = qrcodeMerchantUserMapper.update(null, updateWrapper1);
        if (update == 1) {
            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper();
            updateWrapper.eq(QrcodeMerchantPo::getQcid, userWeixinMpPo.getQcid());
            updateWrapper.setSql("orderCount = orderCount + 1");
            updateWrapper.setSql("tradeAmount = tradeAmount + " + purchaseEventNotify.getOrderAmount());
            qrcodeMerchantMapper.update(null, updateWrapper);
        }

        log.info("商家码用户购买事件:{}, 二维码:{}, 用户:{}",
                JSON.toJSONString(purchaseEventNotify),
                JSON.toJSONString(detail),
                JSON.toJSONString(userWeixinMpPo));
    }

    @Override
    public void newClue(Integer eid, Integer etp) {
        UserWeixinMpPo userWeixinMpPo = userWeixinMpMapper.selectById(eid);
        log.info("商家二维码推广的用户线索, 参数: {}", JSON.toJSONString(userWeixinMpPo));
        if (userWeixinMpPo.getQcid() == null) {
            return;
        }
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(userWeixinMpPo.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<QrcodeMerchantUserPo> registerPoLambdaUpdateWrapper = new LambdaUpdateWrapper();
        registerPoLambdaUpdateWrapper.eq(QrcodeMerchantUserPo::getQcid, userWeixinMpPo.getQcid());
        registerPoLambdaUpdateWrapper.eq(QrcodeMerchantUserPo::getEid, userWeixinMpPo.getEid());
        registerPoLambdaUpdateWrapper.eq(QrcodeMerchantUserPo::getAppointed, WhetherDict.No.code);
        registerPoLambdaUpdateWrapper.set(QrcodeMerchantUserPo::getAppointed, WhetherDict.Yes.code);
        int update = qrcodeMerchantUserMapper.update(null, registerPoLambdaUpdateWrapper);
        if (update == 1) {
            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper();
            updateWrapper.eq(QrcodeMerchantPo::getQcid, userWeixinMpPo.getQcid());
            updateWrapper.setSql("clueCount = clueCount + 1");
            qrcodeMerchantMapper.update(null, updateWrapper);
        }

        log.info("商家二维码推广的用户线索, 二维码:{}, 用户:{}",
                JSON.toJSONString(detail),
                JSON.toJSONString(userWeixinMpPo));
    }

    @Override
    public void syncLocation(Integer qcid, Integer eid, Integer etp, BigDecimal longitude, BigDecimal latitude) {
        LambdaQueryWrapper<QrcodeMerchantUserPo> qmQueryWrapper = new LambdaQueryWrapper();
        qmQueryWrapper.eq(QrcodeMerchantUserPo::getQcid, qcid);
        qmQueryWrapper.eq(QrcodeMerchantUserPo::getEid, eid);
        qmQueryWrapper.eq(QrcodeMerchantUserPo::getEtp, etp);
        QrcodeMerchantUserPo qrcodeMerchantUserPo = qrcodeMerchantUserMapper.selectOne(qmQueryWrapper);

        if (qrcodeMerchantUserPo != null && qrcodeMerchantUserPo.getLongitude() == null) {
            LambdaUpdateWrapper<QrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper();
            qmUpdateWrapper.eq(QrcodeMerchantUserPo::getQmUserId, qrcodeMerchantUserPo.getQmUserId());
            qmUpdateWrapper.set(QrcodeMerchantUserPo::getLongitude, longitude);
            qmUpdateWrapper.set(QrcodeMerchantUserPo::getLatitude, latitude);
            qrcodeMerchantUserMapper.update(null, qmUpdateWrapper);
            log.info("补齐商户码用户定位信息, 关联信息:{}, 位置:{}, {}", JSON.toJSONString(qrcodeMerchantUserPo), longitude, latitude);
        }
    }
}
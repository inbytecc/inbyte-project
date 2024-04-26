package com.inbyte.component.app.marketing.ambassador.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.component.app.marketing.ambassador.dao.QrcodeMerchantMapper;
import com.inbyte.component.app.order.event.OrderPurchaseEvent;
import com.inbyte.component.app.marketing.ambassador.dao.QrcodeMerchantUserMapper;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.QrRegisterMerchantParam;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.QrcodeMerchantPo;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.merchant.user.QrcodeMerchantUserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 商家分享二维码服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeMerchantService {

    @Autowired
    private QrcodeMerchantMapper qrcodeMerchantMapper;
    @Autowired
    private QrcodeMerchantUserMapper qrcodeMerchantUserMapper;

    public QrcodeMerchantPo detail(Integer qcid) {
        if (qcid == null) {
            return null;
        }
        return qrcodeMerchantMapper.selectById(qcid);
    }

    @Transactional(rollbackFor = Exception.class)
    public void buildRelation(Integer qcid, Integer eid, AppTypeEnum etp) {
//        log.info("建立用户二维码关系 qcid:{}, eid:{}, etp:{}", qcid, eid, etp);
//        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(qcid);
//        if (detail == null) {
//            log.warn("扫码的二维码不存在, 建立关系失败");
//            return;
//        }
//
//        UserWeixinMpPo externalUser = userWeixinMpMapper.selectById(eid);
//        if (externalUser == null) {
//            log.info("建立用户二维码关失败, 用户信息不存在, qcid:{}, eid:{}, etp:{}", qcid, eid, etp);
//            return;
//        }
//
//        LambdaQueryWrapper<QrcodeMerchantUserPo> queryWrapper = new LambdaQueryWrapper<QrcodeMerchantUserPo>()
//                .eq(QrcodeMerchantUserPo::getQcid, qcid)
//                .eq(QrcodeMerchantUserPo::getEid, eid)
//                .eq(QrcodeMerchantUserPo::getEtp, etp);
//        QrcodeMerchantUserPo merchantUserPo = qrcodeMerchantUserMapper.selectOne(queryWrapper);
//        if (merchantUserPo != null) {
//            log.info("当前用户二维码关系已建立, 无需重复：qcid:{}, eid:{}, etp:{}", qcid, eid, etp);
//            return;
//        }
//
//        QrcodeMerchantUserPo qrcodeMerchantUserPo = QrcodeMerchantUserPo.builder()
//                .qcid(qcid)
//                .eid(eid)
//                .etp(etp)
//                .longitude(externalUser.getLongitude())
//                .latitude(externalUser.getLatitude())
//                .createTime(LocalDateTime.now())
//                .build();
//        int i = qrcodeMerchantUserMapper.insertSelective(qrcodeMerchantUserPo);
//        if (i > 0) {
//            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
//                    .eq(QrcodeMerchantPo::getQcid, qcid)
//                    .setSql("relation_count = relation_count + 1");
//            qrcodeMerchantMapper.update(null, updateWrapper);
//            log.info("关联用户商户二维码关系成功:{}, {}, {}", qcid, eid, etp);
//        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void viewed(ScanEventNotify scanEventNotify) {
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(scanEventNotify.getQ());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
                .eq(QrcodeMerchantPo::getQcid, scanEventNotify.getQ())
                .setSql("view_count = view_count + 1");
        qrcodeMerchantMapper.update(null, updateWrapper);

        log.info("用户扫描商家码, 扫码信息:{}, 二维码:{}",
                JSON.toJSONString(scanEventNotify),
                JSON.toJSONString(detail));

        this.buildRelation(scanEventNotify.getQ(), scanEventNotify.getEid(), scanEventNotify.getEtp());
    }


    @Transactional(rollbackFor = Exception.class)
    public void registered(QrRegisterMerchantParam qrRegisterMerchantParam) {
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(qrRegisterMerchantParam.getQ());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
                .eq(QrcodeMerchantPo::getQcid, qrRegisterMerchantParam.getQ())
                .setSql("register_count = register_count + 1");
        qrcodeMerchantMapper.update(null, updateWrapper);

        LambdaUpdateWrapper<QrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper<QrcodeMerchantUserPo>()
                .eq(QrcodeMerchantUserPo::getQcid, qrRegisterMerchantParam.getQ())
                .eq(QrcodeMerchantUserPo::getEid, qrRegisterMerchantParam.getEid())
                .eq(QrcodeMerchantUserPo::getEtp, qrRegisterMerchantParam.getEtp())
                .set(QrcodeMerchantUserPo::getRegistered, WhetherDict.Yes.code);
        qrcodeMerchantUserMapper.update(null, qmUpdateWrapper);

        log.info("商家码用户注册事件:{}, 二维码:{}",
                JSON.toJSONString(qrRegisterMerchantParam),
                JSON.toJSONString(detail));
    }

    @Transactional(rollbackFor = Exception.class)
    public void purchased(OrderPurchaseEvent event) {
        LambdaQueryWrapper<QrcodeMerchantUserPo> queryWrapper = new LambdaQueryWrapper<QrcodeMerchantUserPo>()
                .eq(QrcodeMerchantUserPo::getEid, event.getEid())
                .eq(QrcodeMerchantUserPo::getEtp, event.getAppType());
        QrcodeMerchantUserPo qrcodeMerchantUserPo = qrcodeMerchantUserMapper.selectOne(queryWrapper);
        if (qrcodeMerchantUserPo == null) {
            return;
        }
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(qrcodeMerchantUserPo.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }
        LambdaUpdateWrapper<QrcodeMerchantUserPo> qrcodeMerchantUserUpdate = new LambdaUpdateWrapper<QrcodeMerchantUserPo>()
                .eq(QrcodeMerchantUserPo::getQcid, qrcodeMerchantUserPo.getQcid())
                .eq(QrcodeMerchantUserPo::getEid, qrcodeMerchantUserPo.getEid())
                .set(QrcodeMerchantUserPo::getMadeDeal, WhetherDict.Yes.code)
                .setSql("order_count = order_count + 1")
                .setSql("trade_amount = trade_amount + " + event.getOrderAmount());

        int update = qrcodeMerchantUserMapper.update(null, qrcodeMerchantUserUpdate);
        if (update == 1) {
            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
                    .eq(QrcodeMerchantPo::getQcid, qrcodeMerchantUserPo.getQcid())
                    .setSql("order_count = order_count + 1")
                    .setSql("trade_amount = trade_amount + " + event.getOrderAmount());
            qrcodeMerchantMapper.update(null, updateWrapper);
        }

        log.info("商家码用户购买事件:{}, 二维码:{}, 用户:{}",
                JSON.toJSONString(event),
                JSON.toJSONString(detail),
                JSON.toJSONString(qrcodeMerchantUserPo));
    }

    public void newClue(Integer eid, AppTypeEnum etp) {

    }

//    @Override
//    public void newClue(Integer eid, AppTypeEnum etp) {
//        UserWeixinMpPo userWeixinMpPo = userWeixinMpMapper.selectById(eid);
//        log.info("商家二维码推广的用户线索, 参数: {}", JSON.toJSONString(userWeixinMpPo));
//        if (userWeixinMpPo.getQcid() == null) {
//            return;
//        }
//        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(userWeixinMpPo.getQcid());
//        if (detail == null) {
//            log.warn("扫码的二维码不存在");
//            return;
//        }
//
//        LambdaUpdateWrapper<QrcodeMerchantUserPo> registerPoLambdaUpdateWrapper = new LambdaUpdateWrapper<QrcodeMerchantUserPo>()
//                .eq(QrcodeMerchantUserPo::getQcid, userWeixinMpPo.getQcid())
//                .eq(QrcodeMerchantUserPo::getEid, userWeixinMpPo.getEid())
//                .eq(QrcodeMerchantUserPo::getAppointed, WhetherDict.No.code)
//                .set(QrcodeMerchantUserPo::getAppointed, WhetherDict.Yes.code);
//        int update = qrcodeMerchantUserMapper.update(null, registerPoLambdaUpdateWrapper);
//        if (update == 1) {
//            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper();
//            updateWrapper.eq(QrcodeMerchantPo::getQcid, userWeixinMpPo.getQcid());
//            updateWrapper.setSql("clue_count = clue_count + 1");
//            qrcodeMerchantMapper.update(null, updateWrapper);
//        }
//
//        log.info("商家二维码推广的用户线索, 二维码:{}, 用户:{}",
//                JSON.toJSONString(detail),
//                JSON.toJSONString(userWeixinMpPo));
//    }

    public void syncLocation(Integer qcid, Integer eid, AppTypeEnum etp, BigDecimal longitude, BigDecimal latitude) {
        LambdaQueryWrapper<QrcodeMerchantUserPo> qmQueryWrapper = new LambdaQueryWrapper<QrcodeMerchantUserPo>()
                .eq(QrcodeMerchantUserPo::getQcid, qcid)
                .eq(QrcodeMerchantUserPo::getEid, eid)
                .eq(QrcodeMerchantUserPo::getEtp, etp);
        QrcodeMerchantUserPo qrcodeMerchantUserPo = qrcodeMerchantUserMapper.selectOne(qmQueryWrapper);

        if (qrcodeMerchantUserPo != null && qrcodeMerchantUserPo.getLongitude() == null) {
            LambdaUpdateWrapper<QrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper<QrcodeMerchantUserPo>()
                    .eq(QrcodeMerchantUserPo::getQmUserId, qrcodeMerchantUserPo.getQmUserId())
                    .set(QrcodeMerchantUserPo::getLongitude, longitude)
                    .set(QrcodeMerchantUserPo::getLatitude, latitude);
            qrcodeMerchantUserMapper.update(null, qmUpdateWrapper);
            log.info("补齐商户码用户定位信息, 关联信息:{}, 位置:{}, {}", JSON.toJSONString(qrcodeMerchantUserPo), longitude, latitude);
        }
    }
}
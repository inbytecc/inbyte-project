package com.inbyte.component.app.marketing.ambassador.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.component.app.marketing.ambassador.dao.QrcodeMerchantMapper;
import com.inbyte.component.app.marketing.ambassador.dao.QrcodeMerchantUserMapper;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.QrcodeMerchantPo;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.merchant.user.QrcodeMerchantUserPo;
import com.inbyte.component.app.order.event.OrderPurchaseEvent;
import com.inbyte.component.app.user.dict.UserSourceTypeDict;
import com.inbyte.component.app.user.event.UserFirstTimeLoginEvent;
import com.inbyte.component.app.user.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
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


    /**
     * 监听用户首次登录事件
     *
     * 记录推荐绑定关系
     *
     * @param event
     */
    @Async
    @EventListener
    public void onUserFirstTimeLoginEvent(UserFirstTimeLoginEvent event) {
        if (event.getQctp() == UserSourceTypeDict.User_Share.code) {
            return;
        }

        log.info("监听用户首次登录事件, 建立用户二维码关系, event：{}", event);
        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(event.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在, 建立关系失败");
            return;
        }

        QrcodeMerchantUserPo qrcodeMerchantUserPo = QrcodeMerchantUserPo.builder()
                .qcid(event.getQcid())
                .eid(event.getReferredEid())
                .etp(event.getAppType())
                .longitude(event.getLongitude())
                .latitude(event.getLatitude())
                .createTime(LocalDateTime.now())
                .build();
        int i = qrcodeMerchantUserMapper.insertSelective(qrcodeMerchantUserPo);
        if (i > 0) {
            LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
                    .eq(QrcodeMerchantPo::getQcid, event.getQcid())
                    .setSql("relation_count = relation_count + 1");
            qrcodeMerchantMapper.update(null, updateWrapper);
            log.info("关联用户商户二维码关系成功:{}, ", event);
        }
    }


    /**
     * 监听用户注册事件
     *
     * 补全用户ID，以便统计推广数据
     *
     * @param event
     */
    @Async
    @EventListener
    public void onUserRegisterEvent(UserRegisterEvent event) {
        log.info("监听用户注册事件,event：{}", event);
        LambdaQueryWrapper<QrcodeMerchantUserPo> queryWrapper = new LambdaQueryWrapper<QrcodeMerchantUserPo>()
                .eq(QrcodeMerchantUserPo::getEid, event.getEid())
                .eq(QrcodeMerchantUserPo::getEtp, event.getAppType())
                .eq(QrcodeMerchantUserPo::getMctNo, event.getMctNo());
        QrcodeMerchantUserPo qrcodeMerchantUserPo = qrcodeMerchantUserMapper.selectOne(queryWrapper);
        if (qrcodeMerchantUserPo == null) {
            return;
        }

        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(qrcodeMerchantUserPo.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
                .eq(QrcodeMerchantPo::getQcid, qrcodeMerchantUserPo.getQcid())
                .setSql("register_count = register_count + 1");
        qrcodeMerchantMapper.update(null, updateWrapper);

        LambdaUpdateWrapper<QrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper<QrcodeMerchantUserPo>()
                .eq(QrcodeMerchantUserPo::getQmUserId, qrcodeMerchantUserPo.getQmUserId())
                .set(QrcodeMerchantUserPo::getRegistered, Whether.Yes);
        qrcodeMerchantUserMapper.update(null, qmUpdateWrapper);

        log.info("商家码用户注册事件处理完成:{}, 二维码:{}, 用户与码关系:{}",
                JSON.toJSONString(event),
                JSON.toJSONString(detail),
                JSON.toJSONString(qrcodeMerchantUserPo));
    }

    /**
     * 监听用户订单付款事件
     *
     * 记录订单信息
     * @param event
     */
    @Async
    @EventListener
    public void onOrderPurchaseEvent(OrderPurchaseEvent event) {
        log.info("监听用户订单付款事件,event：{}", event);
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

//    @Transactional(rollbackFor = Exception.class)
//    public void viewed(ScanEventNotify scanEventNotify) {
//        QrcodeMerchantPo detail = qrcodeMerchantMapper.selectById(scanEventNotify.getQ());
//        if (detail == null) {
//            log.warn("扫码的二维码不存在");
//            return;
//        }
//
//        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<QrcodeMerchantPo>()
//                .eq(QrcodeMerchantPo::getQcid, scanEventNotify.getQ())
//                .setSql("view_count = view_count + 1");
//        qrcodeMerchantMapper.update(null, updateWrapper);
//
//        log.info("用户扫描商家码, 扫码信息:{}, 二维码:{}",
//                JSON.toJSONString(scanEventNotify),
//                JSON.toJSONString(detail));
//    }

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
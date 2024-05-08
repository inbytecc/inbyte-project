package com.inbyte.component.app.marketing.ambassador.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.component.app.marketing.ambassador.dao.MarketingQrcodeMerchantMapper;
import com.inbyte.component.app.marketing.ambassador.dao.MarketingQrcodeMerchantUserMapper;
import com.inbyte.component.app.marketing.ambassador.model.MarketingQrcodeMerchantPo;
import com.inbyte.component.app.marketing.ambassador.model.MarketingQrcodeMerchantUserPo;
import com.inbyte.component.app.order.event.OrderPaidEvent;
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
    private MarketingQrcodeMerchantMapper marketingQrcodeMerchantMapper;
    @Autowired
    private MarketingQrcodeMerchantUserMapper marketingQrcodeMerchantUserMapper;

    public MarketingQrcodeMerchantPo detail(Integer qcid) {
        if (qcid == null) {
            return null;
        }
        return marketingQrcodeMerchantMapper.selectById(qcid);
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
        MarketingQrcodeMerchantPo detail = marketingQrcodeMerchantMapper.selectById(event.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在, 建立关系失败");
            return;
        }

        MarketingQrcodeMerchantUserPo marketingQrcodeMerchantUserPo = MarketingQrcodeMerchantUserPo.builder()
                .qcid(event.getQcid())
                .eid(event.getReferredEid())
                .etp(event.getAppType())
                .longitude(event.getLongitude())
                .latitude(event.getLatitude())
                .createTime(LocalDateTime.now())
                .build();
        int i = marketingQrcodeMerchantUserMapper.insertSelective(marketingQrcodeMerchantUserPo);
        if (i > 0) {
            LambdaUpdateWrapper<MarketingQrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<MarketingQrcodeMerchantPo>()
                    .eq(MarketingQrcodeMerchantPo::getQcid, event.getQcid())
                    .setSql("relation_count = relation_count + 1");
            marketingQrcodeMerchantMapper.update(null, updateWrapper);
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
        LambdaQueryWrapper<MarketingQrcodeMerchantUserPo> queryWrapper = new LambdaQueryWrapper<MarketingQrcodeMerchantUserPo>()
                .eq(MarketingQrcodeMerchantUserPo::getEid, event.getEid())
                .eq(MarketingQrcodeMerchantUserPo::getEtp, event.getAppType())
                .eq(MarketingQrcodeMerchantUserPo::getMctNo, event.getMctNo());
        MarketingQrcodeMerchantUserPo marketingQrcodeMerchantUserPo = marketingQrcodeMerchantUserMapper.selectOne(queryWrapper);
        if (marketingQrcodeMerchantUserPo == null) {
            return;
        }

        MarketingQrcodeMerchantPo detail = marketingQrcodeMerchantMapper.selectById(marketingQrcodeMerchantUserPo.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }

        LambdaUpdateWrapper<MarketingQrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<MarketingQrcodeMerchantPo>()
                .eq(MarketingQrcodeMerchantPo::getQcid, marketingQrcodeMerchantUserPo.getQcid())
                .setSql("register_count = register_count + 1");
        marketingQrcodeMerchantMapper.update(null, updateWrapper);

        LambdaUpdateWrapper<MarketingQrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper<MarketingQrcodeMerchantUserPo>()
                .eq(MarketingQrcodeMerchantUserPo::getQmUserId, marketingQrcodeMerchantUserPo.getQmUserId())
                .set(MarketingQrcodeMerchantUserPo::getRegistered, Whether.Yes);
        marketingQrcodeMerchantUserMapper.update(null, qmUpdateWrapper);

        log.info("商家码用户注册事件处理完成:{}, 二维码:{}, 用户与码关系:{}",
                JSON.toJSONString(event),
                JSON.toJSONString(detail),
                JSON.toJSONString(marketingQrcodeMerchantUserPo));
    }

    /**
     * 监听用户订单付款事件
     *
     * 记录订单信息
     * @param event
     */
    @Async
    @EventListener
    public void onOrderPurchaseEvent(OrderPaidEvent event) {
        log.info("监听用户订单付款事件,event：{}", event);
        // TODO 有BUG以后处理 chenjw
        LambdaQueryWrapper<MarketingQrcodeMerchantUserPo> queryWrapper = new LambdaQueryWrapper<MarketingQrcodeMerchantUserPo>()
                .eq(MarketingQrcodeMerchantUserPo::getQmUserId, event.getUserId())
                .eq(MarketingQrcodeMerchantUserPo::getEtp, event.getAppType());
        MarketingQrcodeMerchantUserPo marketingQrcodeMerchantUserPo = marketingQrcodeMerchantUserMapper.selectOne(queryWrapper);
        if (marketingQrcodeMerchantUserPo == null) {
            return;
        }
        MarketingQrcodeMerchantPo detail = marketingQrcodeMerchantMapper.selectById(marketingQrcodeMerchantUserPo.getQcid());
        if (detail == null) {
            log.warn("扫码的二维码不存在");
            return;
        }
        LambdaUpdateWrapper<MarketingQrcodeMerchantUserPo> qrcodeMerchantUserUpdate = new LambdaUpdateWrapper<MarketingQrcodeMerchantUserPo>()
                .eq(MarketingQrcodeMerchantUserPo::getQcid, marketingQrcodeMerchantUserPo.getQcid())
                .eq(MarketingQrcodeMerchantUserPo::getEid, marketingQrcodeMerchantUserPo.getEid())
                .set(MarketingQrcodeMerchantUserPo::getMadeDeal, WhetherDict.Yes.code)
                .setSql("order_count = order_count + 1")
                .setSql("trade_amount = trade_amount + " + event.getOrderAmount());
        int update = marketingQrcodeMerchantUserMapper.update(null, qrcodeMerchantUserUpdate);
        if (update == 1) {
            LambdaUpdateWrapper<MarketingQrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<MarketingQrcodeMerchantPo>()
                    .eq(MarketingQrcodeMerchantPo::getQcid, marketingQrcodeMerchantUserPo.getQcid())
                    .setSql("order_count = order_count + 1")
                    .setSql("trade_amount = trade_amount + " + event.getOrderAmount());
            marketingQrcodeMerchantMapper.update(null, updateWrapper);
        }

        log.info("商家码用户购买事件:{}, 二维码:{}, 用户:{}",
                JSON.toJSONString(event),
                JSON.toJSONString(detail),
                JSON.toJSONString(marketingQrcodeMerchantUserPo));
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
        LambdaQueryWrapper<MarketingQrcodeMerchantUserPo> qmQueryWrapper = new LambdaQueryWrapper<MarketingQrcodeMerchantUserPo>()
                .eq(MarketingQrcodeMerchantUserPo::getQcid, qcid)
                .eq(MarketingQrcodeMerchantUserPo::getEid, eid)
                .eq(MarketingQrcodeMerchantUserPo::getEtp, etp);
        MarketingQrcodeMerchantUserPo marketingQrcodeMerchantUserPo = marketingQrcodeMerchantUserMapper.selectOne(qmQueryWrapper);

        if (marketingQrcodeMerchantUserPo != null && marketingQrcodeMerchantUserPo.getLongitude() == null) {
            LambdaUpdateWrapper<MarketingQrcodeMerchantUserPo> qmUpdateWrapper = new LambdaUpdateWrapper<MarketingQrcodeMerchantUserPo>()
                    .eq(MarketingQrcodeMerchantUserPo::getQmUserId, marketingQrcodeMerchantUserPo.getQmUserId())
                    .set(MarketingQrcodeMerchantUserPo::getLongitude, longitude)
                    .set(MarketingQrcodeMerchantUserPo::getLatitude, latitude);
            marketingQrcodeMerchantUserMapper.update(null, qmUpdateWrapper);
            log.info("补齐商户码用户定位信息, 关联信息:{}, 位置:{}, {}", JSON.toJSONString(marketingQrcodeMerchantUserPo), longitude, latitude);
        }
    }
}
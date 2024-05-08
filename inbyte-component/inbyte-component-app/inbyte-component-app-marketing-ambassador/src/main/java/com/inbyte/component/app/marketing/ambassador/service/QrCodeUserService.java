package com.inbyte.component.app.marketing.ambassador.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.component.app.marketing.ambassador.dao.MarketingUserRefererMapper;
import com.inbyte.component.app.marketing.ambassador.model.MarketingUserRefererPo;
import com.inbyte.component.app.order.event.OrderPaidEvent;
import com.inbyte.component.app.user.dict.UserSourceTypeDict;
import com.inbyte.component.app.user.event.UserFirstTimeLoginEvent;
import com.inbyte.component.app.user.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 直接分享
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeUserService {

    @Autowired
    private MarketingUserRefererMapper marketingUserRefererMapper;

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
        if (event.getQctp() == UserSourceTypeDict.Merchant_Share.code) {
            return;
        }
        log.info("用户服务监听到定位更新事件,event：{}", event);
        MarketingUserRefererPo userRefererPo = MarketingUserRefererPo.builder()
                .appType(event.getAppType())
                .introducerEid(event.getIntroducerEid())
                .referredEid(event.getReferredEid())
                .mctNo(event.getMctNo())
                .appId(event.getAppId())
                .createTime(event.getLoginTime())
                .build();
        marketingUserRefererMapper.insert(userRefererPo);
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
        LambdaUpdateWrapper<MarketingUserRefererPo> update = new LambdaUpdateWrapper<MarketingUserRefererPo>()
                .eq(MarketingUserRefererPo::getIntroducerEid, event.getEid())
                .eq(MarketingUserRefererPo::getMctNo, event.getMctNo())
                .set(MarketingUserRefererPo::getIntroducerUserId, event.getUserId());
        marketingUserRefererMapper.update(update);

        update = new LambdaUpdateWrapper<MarketingUserRefererPo>()
                .eq(MarketingUserRefererPo::getReferredEid, event.getEid())
                .eq(MarketingUserRefererPo::getMctNo, event.getMctNo())
                .set(MarketingUserRefererPo::getRegistered, Whether.Yes);
        marketingUserRefererMapper.update(update);

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
        LambdaUpdateWrapper<MarketingUserRefererPo> update = new LambdaUpdateWrapper<MarketingUserRefererPo>()
                .eq(MarketingUserRefererPo::getReferredUserId, event.getUserId())
                .eq(MarketingUserRefererPo::getMctNo, event.getMctNo())
                .eq(MarketingUserRefererPo::getAppType, event.getAppType())
                .setSql("order_count = order_count + 1")
                .setSql("trade_amount = trade_amount + " + event.getOrderAmount());;
        marketingUserRefererMapper.update(update);
    }

}
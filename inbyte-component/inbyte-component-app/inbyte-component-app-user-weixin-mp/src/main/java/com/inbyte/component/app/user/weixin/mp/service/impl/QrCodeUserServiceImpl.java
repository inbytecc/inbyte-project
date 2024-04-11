package com.inbyte.component.app.user.weixin.mp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.component.app.user.weixin.mp.dao.UserWeixinMpInviteMapper;
import com.inbyte.component.app.user.weixin.mp.dao.UserWeixinMpMapper;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpPo;
import com.inbyte.component.app.user.weixin.mp.model.invite.UserWeixinMpInvitePo;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.QrCodePurchaseEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.user.weixin.mp.service.QrCodeUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 直接分享
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeUserServiceImpl implements QrCodeUserService {

    @Autowired
    private UserWeixinMpMapper userWeixinMpMapper;
    @Autowired
    private UserWeixinMpInviteMapper userWeixinMpInviteMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buildRelation(Integer registerEid, Integer recommendEid) {
        UserWeixinMpInvitePo userWeixinMpInvitePo = UserWeixinMpInvitePo.builder()
                .eid(registerEid)
                .recommendEid(recommendEid)
                .createTime(LocalDateTime.now())
                .build();
        userWeixinMpInviteMapper.insert(userWeixinMpInvitePo);

        LambdaUpdateWrapper<UserWeixinMpPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserWeixinMpPo::getEid, recommendEid);
        updateWrapper.setSql("invite_count = invite_count + 1");
        userWeixinMpMapper.update(null, updateWrapper);
    }

    @Override
    public void viewed(ScanEventNotify scanEventNotify) {
        // 用户推荐访问不记录
    }

    @Override
    public void registered(Integer registerEid) {
        LambdaUpdateWrapper<UserWeixinMpInvitePo> weixinMpInviteUpdateWrapper = new LambdaUpdateWrapper<>();
        weixinMpInviteUpdateWrapper.eq(UserWeixinMpInvitePo::getEid, registerEid);
        weixinMpInviteUpdateWrapper.set(UserWeixinMpInvitePo::getRegistered, WhetherDict.Yes.code);
        userWeixinMpInviteMapper.update(null, weixinMpInviteUpdateWrapper);
    }

    @Override
    public void purchased(QrCodePurchaseEventNotify purchaseEventNotify, UserWeixinMpPo userWeixinMpPo) {
        log.info("受推荐的用户成交, 参数:{}, {}", JSON.toJSONString(purchaseEventNotify), JSON.toJSONString(userWeixinMpPo));
        userWeixinMpInviteMapper.increaseOrder(purchaseEventNotify.getEid(), LocalDateTime.now(),
                purchaseEventNotify.getOrderAmount());
    }

    @Override
    public void newClue(Integer eid, AppTypeEnum etp) {
        LambdaUpdateWrapper<UserWeixinMpInvitePo> weixinMpInviteUpdateWrapper = new LambdaUpdateWrapper<>();
        weixinMpInviteUpdateWrapper.eq(UserWeixinMpInvitePo::getEid, eid);
        weixinMpInviteUpdateWrapper.set(UserWeixinMpInvitePo::getAppointed, WhetherDict.Yes.code);
        userWeixinMpInviteMapper.update(null, weixinMpInviteUpdateWrapper);

    }
}
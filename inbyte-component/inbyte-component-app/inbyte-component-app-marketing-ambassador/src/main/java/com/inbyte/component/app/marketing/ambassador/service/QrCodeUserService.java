package com.inbyte.component.app.marketing.ambassador.service;

import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.component.app.order.event.OrderPurchaseEvent;
import com.inbyte.component.app.marketing.ambassador.model.qrcode.ScanEventNotify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 直接分享
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeUserService {

//    @Autowired
//    private UserWeixinMpMapper userWeixinMpMapper;
//    @Autowired
//    private UserWeixinMpInviteMapper userWeixinMpInviteMapper;

    @Transactional(rollbackFor = Exception.class)
    public void buildRelation(Integer registerEid, Integer recommendEid) {
//        UserWeixinMpInvitePo userWeixinMpInvitePo = UserWeixinMpInvitePo.builder()
//                .eid(registerEid)
//                .recommendEid(recommendEid)
//                .createTime(LocalDateTime.now())
//                .build();
//        userWeixinMpInviteMapper.insert(userWeixinMpInvitePo);
//
//        LambdaUpdateWrapper<UserWeixinMpPo> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(UserWeixinMpPo::getEid, recommendEid);
//        updateWrapper.setSql("invite_count = invite_count + 1");
//        userWeixinMpMapper.update(null, updateWrapper);
    }

    public void viewed(ScanEventNotify scanEventNotify) {
        // 用户推荐访问不记录
    }

    public void registered(Integer registerEid) {
//        LambdaUpdateWrapper<UserWeixinMpInvitePo> weixinMpInviteUpdateWrapper = new LambdaUpdateWrapper<>();
//        weixinMpInviteUpdateWrapper.eq(UserWeixinMpInvitePo::getEid, registerEid);
//        weixinMpInviteUpdateWrapper.set(UserWeixinMpInvitePo::getRegistered, WhetherDict.Yes.code);
//        userWeixinMpInviteMapper.update(null, weixinMpInviteUpdateWrapper);
    }

    public void purchased(OrderPurchaseEvent event) {
//        log.info("受推荐的用户成交, 参数:{}, {}", JSON.toJSONString(event), JSON.toJSONString(userWeixinMpPo));
//        userWeixinMpInviteMapper.increaseOrder(event.getEid(), LocalDateTime.now(),
//                event.getOrderAmount());
    }

    public void newClue(Integer eid, AppTypeEnum etp) {
//        LambdaUpdateWrapper<UserWeixinMpInvitePo> weixinMpInviteUpdateWrapper = new LambdaUpdateWrapper<>();
//        weixinMpInviteUpdateWrapper.eq(UserWeixinMpInvitePo::getEid, eid);
//        weixinMpInviteUpdateWrapper.set(UserWeixinMpInvitePo::getAppointed, WhetherDict.Yes.code);
//        userWeixinMpInviteMapper.update(null, weixinMpInviteUpdateWrapper);

    }
}
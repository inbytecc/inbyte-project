package com.inbyte.component.app.order.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.OrderTypeEnum;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.app.order.dao.OrderCenterMapper;
import com.inbyte.commons.model.dict.OrderStatusEnum;
import com.inbyte.component.app.order.model.OrderCenterBrief;
import com.inbyte.component.app.order.model.OrderCenterPo;
import com.inbyte.component.app.order.model.OrderQuery;
import com.inbyte.component.app.order.service.OrderCenterService;
import com.inbyte.component.app.user.framework.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderCenterServiceImpl implements OrderCenterService {

    @Autowired
    private OrderCenterMapper orderCenterMapper;

    @Override
    public R create(Integer userId, String nickname, String avatar, String tel, String mainPhoto,
                    String orderNo, String orderTitle, String orderBrief, OrderTypeEnum orderType, JSONObject extent,
                    BigDecimal orderAmount, BigDecimal payableAmount,
                    String venueId, String venueName, String mctNo, String appId, AppTypeEnum appType) {
        OrderCenterPo order = OrderCenterPo.builder()
                .userId(userId)
                .nickname(nickname)
                .avatar(avatar)
                .tel(tel)
                .mainPhoto(mainPhoto)

                .orderNo(orderNo)
                .orderTitle(orderTitle)
                .orderBrief(orderBrief)
                .orderType(orderType)
                .orderStatus(OrderStatusEnum.WAIT_PAY)
                .extent(extent)

                .orderAmount(orderAmount)
                .payableAmount(payableAmount)

                .venueId(venueId)
                .venueName(venueName)
                .mctNo(mctNo)
                .appId(appId)
                .appType(appType)
                .createTime(LocalDateTime.now())
                .build();
        orderCenterMapper.insert(order);
        return R.ok("创建成功");
    }

    @Override
    public List<OrderCenterBrief> list(OrderQuery orderQuery) {
        PageUtil.startPage(orderQuery);
        orderQuery.setUserId(SessionUtil.getUserId());
        return orderCenterMapper.list(orderQuery);
    }

    @Override
    public R orderStatusSync(Integer userId, String orderNo, OrderStatusEnum orderStatus) {
        LambdaUpdateWrapper<OrderCenterPo> updateWrapper = new LambdaUpdateWrapper<OrderCenterPo>()
                .eq(OrderCenterPo::getOrderNo, orderNo)
                .eq(OrderCenterPo::getUserId, userId)
                .set(OrderCenterPo::getOrderStatus, orderStatus);
        orderCenterMapper.update(updateWrapper);
        return R.ok();
    }

    @Override
    public R delete(String orderNo) {
        LambdaUpdateWrapper<OrderCenterPo> updateWrapper = new LambdaUpdateWrapper<OrderCenterPo>()
                .eq(OrderCenterPo::getOrderNo, orderNo)
                .eq(OrderCenterPo::getUserId, SessionUtil.getUserId());
        orderCenterMapper.delete(updateWrapper);
        return R.ok("删除成功");
    }


}

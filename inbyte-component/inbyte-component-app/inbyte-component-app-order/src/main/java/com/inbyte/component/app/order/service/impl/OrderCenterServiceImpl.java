package com.inbyte.component.app.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.enums.OrderStatusEnum;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.Assert;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.app.order.dao.OrderCenterMapper;
import com.inbyte.component.app.order.model.OrderCenterBrief;
import com.inbyte.component.app.order.model.OrderCenterCreate;
import com.inbyte.component.app.order.model.OrderCenterPo;
import com.inbyte.component.app.order.model.OrderQuery;
import com.inbyte.component.app.order.service.OrderCenterService;
import com.inbyte.component.app.sign.framework.AppUtil;
import com.inbyte.component.app.user.framework.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderCenterServiceImpl implements OrderCenterService {

    @Autowired
    private OrderCenterMapper orderCenterMapper;

    @Override
    public R create(OrderCenterCreate orderCenterCreate) {
        Assert.ok(orderCenterCreate);

        OrderCenterPo order = OrderCenterPo.builder()
                .userId(orderCenterCreate.getUserId())
                .nickname(orderCenterCreate.getNickname())
                .avatar(orderCenterCreate.getAvatar())
                .tel(orderCenterCreate.getTel())
                .mainPhoto(orderCenterCreate.getMainPhoto())

                .orderNo(orderCenterCreate.getOrderNo())
                .orderTitle(orderCenterCreate.getOrderTitle())
                .orderBrief(orderCenterCreate.getOrderBrief())
                .orderType(orderCenterCreate.getOrderType())
                .orderStatus(OrderStatusEnum.WAIT_PAY)
                .extent(orderCenterCreate.getExtent())

                .orderAmount(orderCenterCreate.getOrderAmount())
                .payableAmount(orderCenterCreate.getPayableAmount())

                .venueId(orderCenterCreate.getVenueId())
                .venueName(orderCenterCreate.getVenueName())
                .mctNo(orderCenterCreate.getMctNo())
                .appId(orderCenterCreate.getAppId())
                .appType(orderCenterCreate.getAppType())
                .createTime(LocalDateTime.now())
                .build();
        orderCenterMapper.insert(order);
        return R.ok("创建成功");
    }

    @Override
    public List<OrderCenterBrief> list(OrderQuery orderQuery) {
        PageUtil.startPage(orderQuery);
        orderQuery.setUserId(SessionUtil.getUserId());
        orderQuery.setMctNo(AppUtil.getMctNo());
        return orderCenterMapper.list(orderQuery);
    }

    @Override
    public R orderStatusSync(Integer userId, String orderNo, OrderStatusEnum orderStatus) {
        LambdaUpdateWrapper<OrderCenterPo> updateWrapper = new LambdaUpdateWrapper<OrderCenterPo>()
                .eq(OrderCenterPo::getOrderNo, orderNo)
                .eq(OrderCenterPo::getUserId, userId)
                .set(OrderCenterPo::getOrderStatus, orderStatus);
        int update = orderCenterMapper.update(updateWrapper);
        if (update == 0) {
            throw InbyteException.error("同步订单状态异常");
        }
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

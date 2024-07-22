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
    public R create(OrderCenterCreate param) {
        Assert.ok(param);

        OrderCenterPo order = OrderCenterPo.builder()
                .userId(param.getUserId())
                .nickname(param.getNickname())
                .avatar(param.getAvatar())
                .tel(param.getTel())
                .mainPhoto(param.getMainPhoto())

                .orderNo(param.getOrderNo())
                .orderTitle(param.getOrderTitle())
                .orderBrief(param.getOrderBrief())
                .orderType(param.getOrderType())
                .orderStatus(param.getOrderStatus() == null ?OrderStatusEnum.WAIT_PAY : param.getOrderStatus())
                .extent(param.getExtent())

                .orderAmount(param.getOrderAmount())
                .payableAmount(param.getPayableAmount())

                .venueId(param.getVenueId())
                .venueName(param.getVenueName())
                .mctNo(param.getMctNo())
                .appId(param.getAppId())
                .appType(param.getAppType())
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

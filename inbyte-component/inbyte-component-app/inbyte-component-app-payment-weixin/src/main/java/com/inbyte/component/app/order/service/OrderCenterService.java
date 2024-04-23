package com.inbyte.component.app.order.service;


import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.order.model.OrderQuery;
import com.inbyte.component.app.order.model.OrderCenterBrief;
import com.inbyte.commons.model.dict.OrderStatusEnum;
import com.inbyte.commons.model.dict.OrderTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单中心接口
 * @author chenjw
 * created on 2016/5/5.
 */
public interface OrderCenterService {

    /**
     * 删除订单
     *
     * @param orderNo
     * @return
     */
    R delete(String orderNo);

    R create(Integer userId, String nickname, String avatar, String tel, String mainPhoto,
             String orderNo, String orderTitle, String orderBrief, OrderTypeEnum orderType, JSONObject extent,
             BigDecimal orderAmount, BigDecimal payableAmount,
             String venueId, String venueName, String mctNo, String appId, AppTypeEnum appType);

    /**
     * 订单列表
     *
     * @param orderQueryParam
     * @return
     */
    List<OrderCenterBrief> list(OrderQuery orderQueryParam);

    /**
     * 订单取消
     * @param userId
     * @param orderNo
     * @param orderStatus
     * @return
     */
    R orderStatusSync(Integer userId, String orderNo, OrderStatusEnum orderStatus);


}

package com.inbyte.component.app.order.service;


import com.inbyte.commons.model.enums.OrderStatusEnum;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.order.model.OrderCenterBrief;
import com.inbyte.component.app.order.model.OrderCenterCreate;
import com.inbyte.component.app.order.model.OrderQuery;

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

    R create(OrderCenterCreate orderCenterCreate);

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

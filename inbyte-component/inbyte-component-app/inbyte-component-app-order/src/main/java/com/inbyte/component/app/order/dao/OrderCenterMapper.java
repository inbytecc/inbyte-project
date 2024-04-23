package com.inbyte.component.app.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.order.model.OrderCenterPo;
import com.inbyte.component.app.order.model.OrderQuery;
import com.inbyte.component.app.order.model.OrderCenterBrief;

import java.util.List;

/**
 * 订单汇总
 *
 * 表名 order_center
 * @author chenjw
 * @date 2024-04-16 17:28:41
 */
public interface OrderCenterMapper extends BaseMapper<OrderCenterPo> {

    /**
     * 订单概要列表查询
     *
     * @param orderQuery
     * @return
     */
    List<OrderCenterBrief> list(OrderQuery orderQuery);


}

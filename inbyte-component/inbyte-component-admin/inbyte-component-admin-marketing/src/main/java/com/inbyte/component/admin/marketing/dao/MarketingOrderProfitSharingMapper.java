package com.inbyte.component.admin.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingPo;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingQuery;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingBrief;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingDetail;
import java.util.List;

/**
 * 订单分账记录
 *
 * 表名 marketing_order_profit_sharing
 * @author chenjw
 * @date 2024-11-19 16:11:50
 */
public interface MarketingOrderProfitSharingMapper extends BaseMapper<MarketingOrderProfitSharingPo> {

    /**
     * 详情
     *
     * @param shareLogId
     * @return MarketingOrderProfitSharingDetail
     **/
    MarketingOrderProfitSharingDetail detail(Integer shareLogId);

    /**
     * 查询列表
     * @param query
     * @return List<MarketingOrderProfitSharingBrief>
     **/
    List<MarketingOrderProfitSharingBrief> list(MarketingOrderProfitSharingQuery query);
}

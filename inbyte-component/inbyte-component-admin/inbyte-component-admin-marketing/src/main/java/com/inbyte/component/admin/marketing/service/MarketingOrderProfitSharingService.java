package com.inbyte.component.admin.marketing.service;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingBrief;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingDetail;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingQuery;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingUpdate;

/**
 * 订单分账记录服务
 *
 * @author chenjw
 * @date 2024-11-19 16:11:50
 **/
public interface MarketingOrderProfitSharingService {

    /**
     * 删除
     *
     * @param shareLogId
     * @return R
     **/
    R execute(Integer shareLogId);

    /**
     * 修改
     *
     * @param update
     * @return R
     **/
    R update(MarketingOrderProfitSharingUpdate update);

    /**
     * 详情
     *
     * @param shareLogId
     * @return MarketingOrderProfitSharingDetail
     **/
    R<MarketingOrderProfitSharingDetail> detail(Integer shareLogId);

    /**
     * 列表
     *
     * @param query
     * @return R<Page<MarketingOrderProfitSharingBrief>>
     **/
    R<Page<MarketingOrderProfitSharingBrief>> list(MarketingOrderProfitSharingQuery query);
}

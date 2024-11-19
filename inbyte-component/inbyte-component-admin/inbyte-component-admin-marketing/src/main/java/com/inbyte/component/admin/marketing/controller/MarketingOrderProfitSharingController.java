package com.inbyte.component.admin.marketing.controller;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingBrief;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingDetail;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingQuery;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingUpdate;
import com.inbyte.component.admin.marketing.service.MarketingOrderProfitSharingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单分账记录
 *
 * @author chenjw
 * @date 2024-11-19 16:11:50
 **/
@RestController
@RequestMapping("marketing/order/profit/sharing")
public class MarketingOrderProfitSharingController {

    @Autowired
    private MarketingOrderProfitSharingService marketingOrderProfitSharingService;

    /**
     * 删除
     *
     * @param shareLogId
     * @return R
     **/
    @DeleteMapping("{shareLogId}")
    public R delete(@PathVariable("shareLogId") Integer shareLogId) {
        return marketingOrderProfitSharingService.delete(shareLogId);
    }

    /**
     * 更新
     *
     * @param update
     * @return R
     **/
    @PutMapping
    public R update(@RequestBody @Valid MarketingOrderProfitSharingUpdate update) {
        return marketingOrderProfitSharingService.update(update);
    }

    /**
     * 详情
     *
     * @param shareLogId
     * @return R<MarketingOrderProfitSharingDetail>
     **/
    @GetMapping("{shareLogId}")
    public R<MarketingOrderProfitSharingDetail> detail(@PathVariable("shareLogId") Integer shareLogId) {
        return marketingOrderProfitSharingService.detail(shareLogId);
    }

    /**
     * 列表
     *
     * @param query
     * @return R<Page<MarketingOrderProfitSharingBrief>>
     **/
    @GetMapping
    public R<Page<MarketingOrderProfitSharingBrief>> list(@ModelAttribute @Valid MarketingOrderProfitSharingQuery query) {
        return marketingOrderProfitSharingService.list(query);
    }
}

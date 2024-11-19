package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.marketing.service.MarketingOrderProfitSharingService;
import com.inbyte.component.admin.marketing.dao.MarketingOrderProfitSharingMapper;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingPo;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingQuery;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingUpdate;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingBrief;
import com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing.MarketingOrderProfitSharingDetail;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * 订单分账记录服务
 *
 * @author chenjw
 * @date 2024-11-19 16:11:50
 **/
@Service
public class MarketingOrderProfitSharingServiceImpl implements MarketingOrderProfitSharingService {

    @Autowired
    private MarketingOrderProfitSharingMapper marketingOrderProfitSharingMapper;

    @Override
    public R delete(Integer shareLogId) {
        LambdaQueryWrapper<MarketingOrderProfitSharingPo> queryWrapper = new LambdaQueryWrapper<MarketingOrderProfitSharingPo>()
            .eq(MarketingOrderProfitSharingPo::getShareLogId, shareLogId)
            .eq(MarketingOrderProfitSharingPo::getMctNo, SessionUtil.getMctNo());
        marketingOrderProfitSharingMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(MarketingOrderProfitSharingUpdate update) {
        MarketingOrderProfitSharingPo marketingOrderProfitSharingPo = MarketingOrderProfitSharingPo.builder()
                .updateTime(LocalDateTime.now())
                .modifier(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, marketingOrderProfitSharingPo);

        LambdaQueryWrapper<MarketingOrderProfitSharingPo> queryWrapper = new LambdaQueryWrapper<MarketingOrderProfitSharingPo>()
                .eq(MarketingOrderProfitSharingPo::getShareLogId, update.getShareLogId())
                .eq(MarketingOrderProfitSharingPo::getMctNo, SessionUtil.getMctNo());
        marketingOrderProfitSharingMapper.update(marketingOrderProfitSharingPo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<MarketingOrderProfitSharingDetail> detail(Integer shareLogId) {
        return R.ok(marketingOrderProfitSharingMapper.detail(shareLogId));
    }

    @Override
    public R<Page<MarketingOrderProfitSharingBrief>> list(MarketingOrderProfitSharingQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(marketingOrderProfitSharingMapper.list(query));
    }
}

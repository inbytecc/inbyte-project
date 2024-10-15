package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.marketing.service.MarketingDistributorService;
import com.inbyte.component.admin.marketing.dao.MarketingDistributorMapper;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorPo;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorQuery;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorCreate;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorUpdate;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorBrief;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorDetail;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * 分销商服务
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@Service
public class MarketingDistributorServiceImpl implements MarketingDistributorService {

    @Autowired
    private MarketingDistributorMapper marketingDistributorMapper;

    @Override
    public R create(MarketingDistributorCreate create) {
        MarketingDistributorPo marketingDistributorPo = MarketingDistributorPo.builder()
                .mctNo(SessionUtil.getMctNo())
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(create, marketingDistributorPo);
        marketingDistributorMapper.insert(marketingDistributorPo);
        return R.ok("创建成功");
    }

    @Override
    public R delete(Integer distributorId) {
        LambdaQueryWrapper<MarketingDistributorPo> queryWrapper = new LambdaQueryWrapper<MarketingDistributorPo>()
            .eq(MarketingDistributorPo::getDistributorId, distributorId)
            .eq(MarketingDistributorPo::getMctNo, SessionUtil.getMctNo());
        marketingDistributorMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(MarketingDistributorUpdate update) {
        MarketingDistributorPo marketingDistributorPo = MarketingDistributorPo.builder()
                .updateTime(LocalDateTime.now())
                .modifier(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, marketingDistributorPo);

        LambdaQueryWrapper<MarketingDistributorPo> queryWrapper = new LambdaQueryWrapper<MarketingDistributorPo>()
                .eq(MarketingDistributorPo::getDistributorId, update.getDistributorId())
                .eq(MarketingDistributorPo::getMctNo, SessionUtil.getMctNo());
        marketingDistributorMapper.update(marketingDistributorPo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<MarketingDistributorDetail> detail(Integer distributorId) {
        return R.ok(marketingDistributorMapper.detail(distributorId));
    }

    @Override
    public R<Page<MarketingDistributorBrief>> list(MarketingDistributorQuery query) {
        PageUtil.startPage(query);
        return R.page(marketingDistributorMapper.list(query));
    }
}

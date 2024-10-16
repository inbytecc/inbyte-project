package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.enums.AccountTypeEnum;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.marketing.dao.MarketingDistributorMapper;
import com.inbyte.component.admin.marketing.model.marketing.distributor.*;
import com.inbyte.component.admin.marketing.service.MarketingDistributorService;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.common.payment.weixin.service.PaymentWeixinPartnerProfitSharingService;
import com.wechat.pay.java.service.profitsharing.model.AddReceiverRequest;
import com.wechat.pay.java.service.profitsharing.model.ReceiverRelationType;
import com.wechat.pay.java.service.profitsharing.model.ReceiverType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 分销商服务
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@Service
@Slf4j
public class MarketingDistributorServiceImpl implements MarketingDistributorService {

    @Autowired
    private MarketingDistributorMapper marketingDistributorMapper;
    @Autowired
    private PaymentWeixinPartnerProfitSharingService paymentWeixinPartnerProfitSharingService;

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
        return R.ok(marketingDistributorMapper.detail(distributorId, SessionUtil.getMctNo()));
    }

    @Override
    public R<Page<MarketingDistributorBrief>> list(MarketingDistributorQuery query) {
        PageUtil.startPage(query);
        return R.page(marketingDistributorMapper.list(query));
    }

    @Override
    public R addReceiver(Integer distributorId) {
        MarketingDistributorDetail detail = marketingDistributorMapper.detail(distributorId, SessionUtil.getMctNo());
        if (detail == null) {
            return R.failure("分销商不存在");
        }
        ReceiverType receiverType;
        if (detail.getAccountType() == AccountTypeEnum.MERCHANT) {
            return R.failure("微信账户无法添加为分账接收方");
        } else if (detail.getAccountType() == AccountTypeEnum.PERSONAL) {
            receiverType = ReceiverType.PERSONAL_OPENID;
        } else {
            return R.failure("暂不支持该账户类型");
        }

        AddReceiverRequest account = new AddReceiverRequest();
        account.setSubMchid("1683232124");
        account.setSubAppid("wxce4ba383495d7553");
        account.setType(receiverType);
        account.setAccount(detail.getReceiverAccount());
        account.setName(detail.getReceiverName());
        account.setRelationType(ReceiverRelationType.DISTRIBUTOR);
        R r = paymentWeixinPartnerProfitSharingService.addReceiver(account);
        log.info("设置结果：{}", r);
        return R.ok("设置成功");
    }
}

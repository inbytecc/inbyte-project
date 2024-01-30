package com.inbyte.component.admin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dao.MerchantMapper;
import com.inbyte.component.admin.system.user.service.MerchantService;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.merchant.MerchantDetail;
import com.inbyte.component.admin.system.user.model.merchant.MerchantPo;
import com.inbyte.component.admin.system.user.model.merchant.MerchantUpdate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 商户服务
 *
 * @author chenjw
 * @date 2023-11-04 13:10:27
 **/
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public R update(MerchantUpdate update) {
        MerchantPo merchantPo = MerchantPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, merchantPo);

        LambdaQueryWrapper<MerchantPo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(MerchantPo::getMctNo, SessionUtil.getDefaultMctNo());
        merchantMapper.update(merchantPo, queryWrapper);
        return R.success("修改成功");
    }

    @Override
    public R<MerchantDetail> info() {
        return R.success(merchantMapper.detail(SessionUtil.getDefaultMctNo()));
    }

}

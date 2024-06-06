package com.inbyte.component.admin.system.merchant.service.impl;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.merchant.model.InbyteMerchantUpdate;
import com.inbyte.component.admin.system.merchant.service.InbyteMerchantService;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.common.basic.dao.InbyteMerchantMapper;
import com.inbyte.component.common.basic.model.InbyteMerchantPo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 商户服务
 *
 * @author chenjw
 * @date 2024-06-06 17:53:09
 **/
@Service
public class InbyteMerchantServiceImpl implements InbyteMerchantService {

    @Autowired
    private InbyteMerchantMapper inbyteMerchantMapper;

    @Override
    public R update(InbyteMerchantUpdate update) {
        InbyteMerchantPo inbyteMerchantPo = InbyteMerchantPo.builder()
                .updateTime(LocalDateTime.now())
                .modifier(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, inbyteMerchantPo);

        inbyteMerchantPo.setMctNo(SessionUtil.getMctNo());
        inbyteMerchantMapper.updateById(inbyteMerchantPo);
        return R.ok("修改成功");
    }

    @Override
    public R<InbyteMerchantPo> detail() {
        return R.ok(inbyteMerchantMapper.selectById(SessionUtil.getMctNo()));
    }

}

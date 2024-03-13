package com.inbyte.component.admin.system.user.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.merchant.MerchantPo;
import com.inbyte.component.admin.system.user.model.merchant.MerchantUpdate;

/**
 * 商户服务
 *
 * @author chenjw
 * @date 2023-11-04 13:10:27
 **/
public interface MerchantService {

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(MerchantUpdate update);

    /**
     * 详情
     *
     * @return MerchantDetail
     **/
    R<MerchantPo> info();

}
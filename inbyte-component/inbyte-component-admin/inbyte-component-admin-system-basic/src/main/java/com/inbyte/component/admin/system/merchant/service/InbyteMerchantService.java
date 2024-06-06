package com.inbyte.component.admin.system.merchant.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.merchant.model.InbyteMerchantUpdate;
import com.inbyte.component.common.basic.model.InbyteMerchantPo;

/**
 * 商户服务
 *
 * @author chenjw
 * @date 2024-06-06 17:53:09
 **/
public interface InbyteMerchantService {

    /**
     * 修改
     *
     * @param update
     * @return R
     **/
    R update(InbyteMerchantUpdate update);

    /**
     * 详情
     *
     * @return InbyteMerchantDetail
     **/
    R<InbyteMerchantPo> detail();

}

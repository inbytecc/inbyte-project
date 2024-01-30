package com.inbyte.component.admin.system.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.user.model.merchant.MerchantDetail;
import com.inbyte.component.admin.system.user.model.merchant.MerchantPo;

/**
 * 商户
 *
 * 表名：  merchant
 * @author chenjw
 * @date 2023-11-05 12:29:19
 */
public interface MerchantMapper extends BaseMapper<MerchantPo> {

    /**
     * 详情
     *
     * @param mctNo
     * @return MerchantDetail
     **/
    MerchantDetail detail(String mctNo);

}

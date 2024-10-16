package com.inbyte.component.app.marketing.ambassador.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.marketing.ambassador.model.MarketingQrcodeMerchantUserPo;

/**
 * 商户二维码注册用户
 *
 * 表名：  qrcode_merchant_user
 * @author chenjw
 * @date 2023-03-29 16:11:10
 */
public interface MarketingQrcodeMerchantUserMapper extends BaseMapper<MarketingQrcodeMerchantUserPo> {

    int insertSelective(MarketingQrcodeMerchantUserPo po);
}
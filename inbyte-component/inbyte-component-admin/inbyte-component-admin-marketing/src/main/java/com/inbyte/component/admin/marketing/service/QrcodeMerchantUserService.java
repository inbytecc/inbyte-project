package com.inbyte.component.admin.marketing.service;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserBrief;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserDetail;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserQuery;

/**
 * 商户二维码注册用户服务
 *
 * @author chenjw
 * @date 2023-04-04 16:39:21
 **/
public interface QrcodeMerchantUserService {

    /**
     * 详情
     *
     * @param qmUserId
     * @return QrcodeMerchantRegisterDetail
     **/
    R<QrcodeMerchantUserDetail> detail(Integer qmUserId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<QrcodeMerchantRegisterBrief>>>
     **/
    R<Page<QrcodeMerchantUserBrief>> list(QrcodeMerchantUserQuery query);
}

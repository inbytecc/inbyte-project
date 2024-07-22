package com.inbyte.component.admin.marketing.service.impl;

import com.inbyte.component.admin.marketing.dao.MarketingQrcodeMerchantUserMapper;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserBrief;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserDetail;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserQuery;
import com.inbyte.component.admin.marketing.service.QrcodeMerchantUserService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.user.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户二维码注册用户服务
 *
 * @author chenjw
 * @date 2023-04-04 16:39:21
 **/
@Service
public class QrcodeMerchantUserServiceImpl implements QrcodeMerchantUserService {

    @Autowired
    private MarketingQrcodeMerchantUserMapper marketingQrcodeMerchantUserMapper;

    @Override
    public R<QrcodeMerchantUserDetail> detail(Integer qmUserId) {
        return R.ok(marketingQrcodeMerchantUserMapper.detail(qmUserId));
    }

    @Override
    public R<Page<QrcodeMerchantUserBrief>> list(QrcodeMerchantUserQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(marketingQrcodeMerchantUserMapper.list(query));
    }
}

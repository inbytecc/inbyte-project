package com.inbyte.component.admin.marketing.controller;

import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserBrief;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserDetail;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserQuery;
import com.inbyte.component.admin.marketing.service.QrcodeMerchantUserService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商户二维码用户
 *
 * @author chenjw
 * @date 2023-04-04 16:39:21
 **/
@RestController
@RequestMapping("qrcode/merchant/user")
public class QrcodeMerchantUserController {

    @Autowired
    private QrcodeMerchantUserService qrcodeMerchantUserService;

    /**
     * 详情
     *
     * @param qmUserId
     * @return Result<QrcodeMerchantRegisterDetail>
     **/
    @GetMapping("{qmUserId}")
    public R<QrcodeMerchantUserDetail> detail(@PathVariable("qmUserId") Integer qmUserId) {
        return qrcodeMerchantUserService.detail(qmUserId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<QrcodeMerchantRegisterBrief>>>
     **/
    @GetMapping
    public R<Page<List<QrcodeMerchantUserBrief>>> list(@ModelAttribute @Valid QrcodeMerchantUserQuery query) {
        return qrcodeMerchantUserService.list(query);
    }
}

package com.inbyte.component.admin.system.merchant.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.merchant.model.InbyteMerchantUpdate;
import com.inbyte.component.admin.system.merchant.service.InbyteMerchantService;
import com.inbyte.component.common.basic.model.InbyteMerchantPo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商户
 *
 * @author chenjw
 * @date 2024-06-06 17:53:09
 **/
@RestController
@RequestMapping("inbyte/merchant")
public class InbyteMerchantController {

    @Autowired
    private InbyteMerchantService inbyteMerchantService;

    /**
     * 更新
     *
     * @param update
     * @return R
     **/
    @PutMapping
    public R update(@RequestBody @Valid InbyteMerchantUpdate update) {
        return inbyteMerchantService.update(update);
    }

    /**
     * 详情
     *
     * @return R<InbyteMerchantDetail>
     **/
    @GetMapping("current")
    public R<InbyteMerchantPo> detail() {
        return inbyteMerchantService.detail();
    }

}

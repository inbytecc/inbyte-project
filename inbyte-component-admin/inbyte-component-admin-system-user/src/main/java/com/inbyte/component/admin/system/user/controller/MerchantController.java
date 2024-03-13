package com.inbyte.component.admin.system.user.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.merchant.MerchantPo;
import com.inbyte.component.admin.system.user.model.merchant.MerchantUpdate;
import com.inbyte.component.admin.system.user.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商户
 *
 * @author chenjw
 * @date 2023-11-04 13:10:27
 **/
@RestController
@RequestMapping("merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid MerchantUpdate update) {
        return merchantService.update(update);
    }

    /**
     * 详情
     *
     * @return Result<MerchantDetail>
     **/
    @GetMapping("info")
    public R<MerchantPo> info() {
        return merchantService.info();
    }

}

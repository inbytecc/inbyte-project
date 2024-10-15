package com.inbyte.component.admin.marketing.controller;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.marketing.distributor.*;
import com.inbyte.component.admin.marketing.service.MarketingDistributorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分销商
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@RestController
@RequestMapping("marketing/distributor")
public class MarketingDistributorController {

    @Autowired
    private MarketingDistributorService marketingDistributorService;

    /**
     * 创建
     *
     * @param create
     * @return R
     **/
    @PostMapping
    public R create(@RequestBody @Valid MarketingDistributorCreate create) {
        return marketingDistributorService.create(create);
    }

    /**
     * 删除
     *
     * @param distributorId
     * @return R
     **/
    @DeleteMapping("{distributorId}")
    public R delete(@PathVariable("distributorId") Integer distributorId) {
        return marketingDistributorService.delete(distributorId);
    }

    /**
     * 更新
     *
     * @param update
     * @return R
     **/
    @PutMapping
    public R update(@RequestBody @Valid MarketingDistributorUpdate update) {
        return marketingDistributorService.update(update);
    }

    /**
     * 详情
     *
     * @param distributorId
     * @return R<MarketingDistributorDetail>
     **/
    @GetMapping("{distributorId}")
    public R<MarketingDistributorDetail> detail(@PathVariable("distributorId") Integer distributorId) {
        return marketingDistributorService.detail(distributorId);
    }

    /**
     * 列表
     *
     * @param query
     * @return R<Page<MarketingDistributorBrief>>
     **/
    @GetMapping
    public R<Page<MarketingDistributorBrief>> list(@ModelAttribute @Valid MarketingDistributorQuery query) {
        return marketingDistributorService.list(query);
    }
}

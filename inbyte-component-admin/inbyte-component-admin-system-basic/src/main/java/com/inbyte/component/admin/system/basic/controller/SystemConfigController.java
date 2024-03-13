package com.inbyte.component.admin.system.basic.controller;
import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.basic.model.*;
import com.inbyte.component.admin.system.basic.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 系统配置
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@RestController
@RequestMapping("system/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 新增
     *
     * @param insert
     * @return R
     **/
    @PostMapping
    public R insert(@RequestBody @Valid SystemConfigInsert insert) {
        return systemConfigService.insert(insert);
    }

    /**
     * 删除
     *
     * @param id
     * @return R
     **/
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") Integer id) {
        return systemConfigService.delete(id);
    }

    /**
     * 更新
     *
     * @param update
     * @return R
     **/
    @PutMapping
    public R update(@RequestBody @Valid SystemConfigUpdate update) {
        return systemConfigService.update(update);
    }

    /**
     * 更新
     *
     * @param update
     * @return R
     **/
    @PutMapping("by-key")
    public R updateByKey(@RequestBody @Valid SystemConfigUpdateByKey update) {
        return systemConfigService.updateByKey(update);
    }

    /**
     * 详情
     *
     * @param id
     * @return R<SystemConfigDetail>
     **/
    @GetMapping("{id}")
    public R<SystemConfigDetail> detail(@PathVariable("id") Integer id) {
        return systemConfigService.detail(id);
    }

    /**
     * 列表
     *
     * @param query
     * @return R<Page<SystemConfigBrief>>
     **/
    @GetMapping
    public R<Page<SystemConfigBrief>> list(@ModelAttribute @Valid SystemConfigQuery query) {
        return systemConfigService.list(query);
    }

    /**
     * Key获取 String Value
     *
     * @param key
     * @return R<String>
     **/
    @GetMapping("{key}/value")
    public R<String> value(@PathVariable("key") String key) {
        return R.okStr(systemConfigService.getValue(key));
    }

    /**
     * Key获取Value
     *
     * @param key
     * @return R<String>
     **/
    @GetMapping("{key}/json")
    public R<Object> json(@PathVariable("key") String key) {
        return R.ok(JSON.parse(systemConfigService.getValue(key)));
    }

}

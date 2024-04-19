package com.inbyte.component.admin.dict.controller;

import com.inbyte.component.admin.dict.model.*;
import com.inbyte.component.admin.dict.service.DictAdminService;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@RestController
@RequestMapping("dict/admin")
public class InbyteDictAdminController {

    @Autowired
    private DictAdminService dictAdminService;

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid DictInsert insert) {
        return dictAdminService.insert(insert);
    }

    /**
     * 删除
     *
     * @param dictId
     * @return Result
     **/
    @DeleteMapping("{dictId}")
    public R delete(@PathVariable("dictId") String dictId) {
        return dictAdminService.delete(dictId);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid DictUpdate update) {
        return dictAdminService.update(update);
    }

    /**
     * 详情
     *
     * @param dictId
     * @return Result<DictDetail>
     **/
    @GetMapping("{dictId}/detail")
    public R<DictDetail> detail(@PathVariable("dictId") String dictId) {
        return dictAdminService.detail(dictId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<DictBrief>>>
     **/
    @GetMapping
    public R<List<DictBrief>> list(@ModelAttribute @Valid DictQuery query) {
        return dictAdminService.list(query);
    }
}

package com.inbyte.component.admin.dict.controller;
import com.inbyte.component.admin.dict.model.dict.item.DictItemDetail;
import com.inbyte.component.admin.dict.model.dict.item.DictItemInsert;
import com.inbyte.component.admin.dict.model.dict.item.DictItemQuery;
import com.inbyte.component.admin.dict.model.dict.item.DictItemUpdate;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.dict.service.DictItemService;
import com.inbyte.component.admin.dict.model.dict.item.DictItemBrief;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 字典项管理
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@RestController
@RequestMapping("dict/item")
public class DictItemAdminController {

    @Autowired
    private DictItemService dictItemService;

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid DictItemInsert insert) {
        return dictItemService.insert(insert);
    }

    /**
     * 删除
     *
     * @param itemId
     * @return Result
     **/
    @DeleteMapping("{itemId}")
    public R delete(@PathVariable("itemId") Integer itemId) {
        return dictItemService.delete(itemId);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid DictItemUpdate update) {
        return dictItemService.update(update);
    }

    /**
     * 详情
     *
     * @param itemId
     * @return Result<DictItemDetail>
     **/
    @GetMapping("{itemId}")
    public R<DictItemDetail> detail(@PathVariable("itemId") Integer itemId) {
        return dictItemService.detail(itemId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<DictItemBrief>>>
     **/
    @GetMapping
    public R<Page<DictItemBrief>> list(@ModelAttribute @Valid DictItemQuery query) {
        return dictItemService.list(query);
    }
}

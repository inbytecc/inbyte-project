package com.inbyte.component.admin.marketing.controller;

import com.inbyte.component.admin.marketing.model.clue.log.*;
import com.inbyte.component.admin.marketing.service.CustomerClueLogService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 跟进记录
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
@RestController
@RequestMapping("customer-clue/log")
public class CustomerClueLogController {

    @Autowired
    private CustomerClueLogService customerClueLogService;

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid CustomerClueLogInsert insert) {
        return customerClueLogService.insert(insert);
    }

    /**
     * 删除
     *
     * @param logId
     * @return Result
     **/
    @DeleteMapping("{logId}")
    public R delete(@PathVariable("logId") Integer logId) {
        return customerClueLogService.delete(logId);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid CustomerClueLogUpdate update) {
        return customerClueLogService.update(update);
    }

    /**
     * 详情
     *
     * @param logId
     * @return Result<CustomerClueContactLogDetail>
     **/
    @GetMapping("{logId}")
    public R<CustomerClueLogDetail> detail(@PathVariable("logId") Integer logId) {
        return customerClueLogService.detail(logId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<CustomerClueContactLogBrief>>>
     **/
    @GetMapping
    public R<Page<CustomerClueLogBrief>> list(@ModelAttribute @Valid CustomerClueLogQuery query) {
        return customerClueLogService.list(query);
    }
}

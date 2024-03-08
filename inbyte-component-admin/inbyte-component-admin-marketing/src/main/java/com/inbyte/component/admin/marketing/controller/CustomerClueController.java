package com.inbyte.component.admin.marketing.controller;

import com.inbyte.component.admin.marketing.model.clue.*;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.service.CustomerClueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户线索
 *
 * @author chenjw
 * @date 2023-03-09 13:17:26
 **/
@RestController
@RequestMapping("customer/clue")
public class CustomerClueController {

    @Autowired
    private CustomerClueService customerclueService;

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid CustomerClueInsert insert) {
        return customerclueService.insert(insert);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid CustomerClueUpdate update) {
        return customerclueService.update(update);
    }

    /**
     * 详情
     *
     * @param clueId
     * @return Result<CustomerClueDetail>
     **/
    @GetMapping("{clueId}")
    public R<CustomerClueDetail> detail(@PathVariable("clueId") Integer clueId) {
        return customerclueService.detail(clueId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<CustomerClueBrief>>>
     **/
    @GetMapping
    public R<Page<CustomerClueBrief>> list(@ModelAttribute @Valid CustomerClueQuery query) {
        return customerclueService.list(query);
    }
}

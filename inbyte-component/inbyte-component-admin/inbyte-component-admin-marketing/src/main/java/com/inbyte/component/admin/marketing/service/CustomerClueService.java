package com.inbyte.component.admin.marketing.service;

import com.inbyte.component.admin.marketing.model.clue.*;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;

/**
 * 客户线索服务
 *
 * @author chenjw
 * @date 2023-03-09 13:17:26
 **/
public interface CustomerClueService {

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    R insert(CustomerClueInsert insert);

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(CustomerClueUpdate update);

    /**
     * 详情
     *
     * @param clueId
     * @return CustomerClueDetail
     **/
    R<CustomerClueDetail> detail(Integer clueId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<CustomerClueBrief>>>
     **/
    R<Page<CustomerClueBrief>> list(CustomerClueQuery query);

}

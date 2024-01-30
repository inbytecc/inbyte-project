package com.inbyte.component.admin.marketing.service;

import com.inbyte.component.admin.marketing.model.clue.log.*;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;

import java.util.List;

/**
 * 跟进记录服务
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
public interface CustomerClueLogService {

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    R insert(CustomerClueLogInsert insert);

    /**
     * 删除
     *
     * @param logId
     * @return Result
     **/
    R delete(Integer logId);

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(CustomerClueLogUpdate update);

    /**
     * 详情
     *
     * @param logId
     * @return CustomerClueContactLogDetail
     **/
    R<CustomerClueLogDetail> detail(Integer logId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<CustomerClueContactLogBrief>>>
     **/
    R<Page<List<CustomerClueLogBrief>>> list(CustomerClueLogQuery query);
}

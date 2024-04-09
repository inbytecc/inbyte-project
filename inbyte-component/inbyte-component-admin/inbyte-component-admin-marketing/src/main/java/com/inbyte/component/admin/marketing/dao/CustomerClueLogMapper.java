package com.inbyte.component.admin.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.marketing.model.clue.log.CustomerClueLogBrief;
import com.inbyte.component.admin.marketing.model.clue.log.CustomerClueLogDetail;
import com.inbyte.component.admin.marketing.model.clue.log.CustomerClueLogPo;
import com.inbyte.component.admin.marketing.model.clue.log.CustomerClueLogQuery;

import java.util.List;

/**
 * 跟进记录
 *
 * 表名：  customer_clue_log
 * @author chenjw
 * @date 2023-03-09 15:56:58
 */
public interface CustomerClueLogMapper extends BaseMapper<CustomerClueLogPo> {

    /**
     * 详情
     *
     * @param logId
     * @return CustomerClueContactLogDetail
     **/
    CustomerClueLogDetail detail(Integer logId);

    /**
     * 查询列表
     * @param query
     * @return List<CustomerClueContactLogBrief>
     **/
    List<CustomerClueLogBrief> list(CustomerClueLogQuery query);
}

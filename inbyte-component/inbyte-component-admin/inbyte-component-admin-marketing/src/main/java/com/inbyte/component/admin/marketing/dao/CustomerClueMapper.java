package com.inbyte.component.admin.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.marketing.model.clue.CustomerClueBrief;
import com.inbyte.component.admin.marketing.model.clue.CustomerClueDetail;
import com.inbyte.component.admin.marketing.model.clue.CustomerCluePo;
import com.inbyte.component.admin.marketing.model.clue.CustomerClueQuery;

import java.util.List;

/**
 * 客户线索
 *
 * 表名：  customer_clue
 * @author chenjw
 * @date 2023-03-09 13:22:05
 */
public interface CustomerClueMapper extends BaseMapper<CustomerCluePo> {

    /**
     * 概要
     *
     * @param clueId
     * @return CustomerClueBrief
     **/
    CustomerClueBrief brief(Integer clueId);

    /**
     * 详情
     *
     * @param clueId
     * @return CustomerClueDetail
     **/
    CustomerClueDetail detail(Integer clueId);

    /**
     * 查询列表
     * @param query
     * @return List<CustomerClueBrief>
     **/
    List<CustomerClueBrief> list(CustomerClueQuery query);
}

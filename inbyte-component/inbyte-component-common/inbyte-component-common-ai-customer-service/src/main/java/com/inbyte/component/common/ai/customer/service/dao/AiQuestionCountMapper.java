package com.inbyte.component.common.ai.customer.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.common.ai.customer.service.model.po.AiQuestionCountPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiQuestionCountMapper extends BaseMapper<AiQuestionCountPo> {
    
    /**
     * 插入或更新问题统计
     * 如果记录已存在则更新计数，不存在则插入新记录
     *
     * @param record 问题统计记录
     * @return 影响行数
     */
    int insertOrUpdate(AiQuestionCountPo record);

}

package com.inbyte.component.common.ai.customer.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.common.ai.customer.service.model.po.AiChatHistoryPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiChatHistoryMapper extends BaseMapper<AiChatHistoryPo> {
    
}
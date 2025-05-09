package com.inbyte.component.common.ai.customer.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.common.ai.customer.service.model.po.AiKnowledgePo;
import org.apache.ibatis.annotations.Param;

/**
 * 客服知识库数据访问层
 * <p>
 * 表名 ai_script_library
 *
 * @author claude
 * @date 2024-04-14
 */
public interface AiKnowledgeMapper extends BaseMapper<AiKnowledgePo> {


    /**
     * 根据问题模糊查询知识
     *
     * @param question 问题内容
     * @param mctNo    商户号
     * @return 匹配的知识
     */
    AiKnowledgePo findByQuestion(@Param("question") String question,
                                 @Param("keyword") String keyword,
                                 @Param("mctNo") String mctNo);

}
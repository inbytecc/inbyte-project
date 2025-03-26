package com.inbyte.component.common.ai.customer.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.common.ai.customer.service.model.po.AiScriptLibraryPo;
import org.apache.ibatis.annotations.Param;

/**
 * 客服话术库数据访问层
 * <p>
 * 表名 ai_script_library
 *
 * @author claude
 * @date 2024-04-14
 */
public interface AiScriptLibraryMapper extends BaseMapper<AiScriptLibraryPo> {


    /**
     * 根据问题模糊查询话术
     *
     * @param question 问题内容
     * @param mctNo    商户号
     * @return 匹配的话术
     */
    AiScriptLibraryPo findByQuestion(@Param("question") String question,
                                     @Param("mctNo") String mctNo);

    /**
     * 根据关键词精确查询话术
     *
     * @param keyword 关键词
     * @param mctNo   商户号
     * @return 匹配的话术
     */
    AiScriptLibraryPo findByKeyword(@Param("keyword") String keyword,
                                    @Param("mctNo") String mctNo);

    /**
     * 增加话术命中次数
     *
     * @param scriptId 话术ID
     * @return 影响的行数
     */
    int incrementHitCount(@Param("scriptId") Integer scriptId);

}
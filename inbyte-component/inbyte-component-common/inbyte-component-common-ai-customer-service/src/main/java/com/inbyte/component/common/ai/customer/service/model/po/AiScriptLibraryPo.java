package com.inbyte.component.common.ai.customer.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.util.convert.ListTypeHandler;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI客服话术库持久化对象
 *
 * @author claude
 * @date 2024-05-15
 **/
@Getter
@Setter
@TableName("ai_script_library")
public class AiScriptLibraryPo {

    /** 话术ID */
    @TableId(type = IdType.AUTO)
    private Integer scriptId;

    /** 问题 */
    private String question;

    /** 回答 */
    private String answer;
    
    /** 关键词 */
    @TableField(typeHandler = ListTypeHandler.class)
    private List<String> keywordList;

    /** 微信小程序短链 */
    private String weixinMpShortLink;

    /** 删除标记 */
    private Integer deleted;
    
    /** 门店ID */
    private String venueId;
    
    /** 商户号 */
    private String mctNo;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 创建人 */
    private String creator;
    
    /** 更新时间 */
    private LocalDateTime updateTime;
    
    /** 修改人 */
    private String modifier;
    
}
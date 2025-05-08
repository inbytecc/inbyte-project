package com.inbyte.component.common.ai.customer.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * AI客服配置实体类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ai_robot_config")
public class AiRobotConfigPo {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 百炼APIKEY
     */
    private String apiKey;

    /**
     * 百炼APPID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 门店ID
     */
    private String venueId;

    /**
     * 客服系统提示词
     */
    private String customerServiceSystemPrompt;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
} 
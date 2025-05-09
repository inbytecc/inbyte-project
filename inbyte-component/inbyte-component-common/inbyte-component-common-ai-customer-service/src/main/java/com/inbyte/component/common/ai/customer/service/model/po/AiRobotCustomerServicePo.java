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
@TableName("ai_robot_customer_service")
public class AiRobotCustomerServicePo {

    /**
     * ID
     */
    @TableId(value = "cs_id", type = IdType.AUTO)
    private Integer csId;

    /**
     * 用户APIKEY
     */
    private String apiKey;

    /**
     * 客服微信名字
     */
    private String customerWechatName;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 门店ID
     */
    private String venueId;

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
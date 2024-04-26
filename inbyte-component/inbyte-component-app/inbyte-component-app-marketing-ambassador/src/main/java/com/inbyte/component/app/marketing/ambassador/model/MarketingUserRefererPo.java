package com.inbyte.component.app.marketing.ambassador.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户二维码实体
 *
 * @author chenjw
 * @date 2024-04-26 16:55:25
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("marketing_user_referer")
public class MarketingUserRefererPo {

    /**
     * 推荐关系ID
     */
    @TableId(value = "referer_id", type = IdType.AUTO)
    private Integer refererId;

    /**
     * 介绍人ID
     */
    private Integer introducerUserId;

    /**
     * 介绍人外部用户ID
     */
    private Integer introducerEid;

    /**
     * 被推荐人
     */
    private Integer referredEid;

    /**
     * 应用类型
     */
    private AppTypeEnum appType;

    /**
     * 已注册
     */
    private Integer registered;

    /**
     * 订单数
     */
    private Integer orderCount;

    /**
     * 交易总额
     */
    private BigDecimal tradeAmount;

    /**
     * 小程序ID
     */
    private String appId;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}

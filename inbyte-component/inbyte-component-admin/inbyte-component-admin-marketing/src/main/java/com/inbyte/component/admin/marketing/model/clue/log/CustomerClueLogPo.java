package com.inbyte.component.admin.marketing.model.clue.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 跟进记录实体
 *
 * @author chenjw
 * @date 2023-03-22 15:25:56
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("customer_clue_log")
public class CustomerClueLogPo {

    /**
     * 跟进记录ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    /**
     * 线索ID
     */
    private Integer clueId;

    /**
     * 跟进方式
     */
    private Integer contactWay;

    /**
     * 跟进阶段
     */
    private Integer contactStage;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 跟进内容
     */
    private String remark;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextContactTime;

    /**
     * 已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Integer creatorId;

    /**
     * 创建人名字
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
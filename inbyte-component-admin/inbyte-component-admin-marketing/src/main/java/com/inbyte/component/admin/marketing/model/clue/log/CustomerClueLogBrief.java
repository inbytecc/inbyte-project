package com.inbyte.component.admin.marketing.model.clue.log;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 跟进记录摘要
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
@Getter
@Setter
public class CustomerClueLogBrief {

    /** 跟进记录ID */
    private Integer logId;

    /** 线索ID */
    private Integer clueId;

    /** 跟进方式 */
    private Integer contactWay;

    /** 跟进阶段 */
    private Integer contactStage;

    /** 商户ID */
    private String mctNo;

    /** 跟进内容 */
    private String remark;

    /** 下次跟进时间 */
    private LocalDateTime nextContactTime;

    /** 创建时间 */
    private LocalDateTime createTime;

}

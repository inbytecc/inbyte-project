package com.inbyte.component.admin.marketing.model.clue.log;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


/**
 * 跟进记录修改
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
@Getter
@Setter
public class CustomerClueLogUpdate {

    /** 跟进记录ID */
    @NotNull(message = "跟进记录ID不能为空")
    private Integer logId;

    /** 线索ID */
    @NotNull(message = "线索ID不能为空")
    private Integer clueId;

    /** 跟进方式 */
    @NotNull(message = "跟进方式不能为空")
    private Integer contactWay;

    /** 跟进阶段 */
    @NotNull(message = "跟进阶段不能为空")
    private Integer contactStage;

    /** 商户ID */
    @NotNull(message = "商户ID不能为空")
    private String mctNo;

    /** 跟进内容 */
    @NotNull(message = "跟进内容不能为空")
    @Length(max = 256, message = "跟进内容长度不能超过256位")
    private String remark;

    /** 下次跟进时间 */
    private LocalDateTime nextContactTime;

}

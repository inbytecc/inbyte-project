package com.inbyte.component.admin.marketing.model.clue.log;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 跟进记录创建
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
@Getter
@Setter
public class CustomerClueLogInsert {

    /** 线索ID */
    @NotNull(message = "线索ID不能为空")
    private Integer clueId;

    /** 跟进方式 */
    @NotNull(message = "跟进方式不能为空")
    private Integer contactWay;

    /** 跟进阶段 */
    @NotNull(message = "跟进阶段不能为空")
    private Integer contactStage;

    /** 跟进内容 */
    @NotNull(message = "跟进内容不能为空")
    @Length(max = 1024, message = "跟进内容长度不能超过1024位")
    private String remark;

    /** 下次跟进时间 */
    @NotNull(message = "下次跟进时间不能为空")
    @Future(message = "下次跟进时间必须是未来时间")
    private LocalDateTime nextContactTime;

}

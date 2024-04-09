package com.inbyte.component.admin.marketing.model.clue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * 学生线索创建
 *
 * @author chenjw
 * @date 2023-07-17 15:41:21
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClueStudentInsert {

    /**
     * 家长用户ID
     */
    private Integer clueId;

    /** 学生姓名 */
    @NotNull(message = "学生姓名不能为空")
    @Length(max = 16, message = "学生姓名长度不能超过16位")
    private String studentName;

    /** 小名 */
    @Length(max = 32, message = "小名长度不能超过32位")
    private String childhoodName;

    /** 性别 */
    private Integer gender;

    /** 出生日期 */
    @Past(message = "出生日期必须是过去吧")
    private LocalDate birthdate;

}

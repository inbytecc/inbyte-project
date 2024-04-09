package com.inbyte.component.admin.user.model.mp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用户增长趋势
 *
 * @author chenjw
 * @date 2023-03-29 13:58:35
 **/
@Getter
@Setter
public class UserTrendBrief {

    /** 日期 */
    private LocalDate date;

    /** 用户数 */
    private Integer count;

}

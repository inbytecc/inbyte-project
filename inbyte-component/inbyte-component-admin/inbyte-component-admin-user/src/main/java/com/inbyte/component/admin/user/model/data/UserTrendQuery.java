package com.inbyte.component.admin.user.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用户定位查询
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@Getter
@Setter
public class UserTrendQuery {

    /**
    * 活跃开始日期
    */
    private LocalDate startDate;

    /**
    * 活跃截止日期
    */
    private LocalDate endDate;
}

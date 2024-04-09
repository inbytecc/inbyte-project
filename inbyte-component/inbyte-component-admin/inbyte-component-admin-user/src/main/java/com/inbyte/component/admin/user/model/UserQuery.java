package com.inbyte.component.admin.user.model;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用户查询
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
@Getter
@Setter
public class UserQuery extends BasePage {

    /**
     * 查询关键字
     **/
    private String keyword;

    /**
    * 开始日期
    */
    private LocalDate startDate;

    /**
    * 截止日期
    */
    private LocalDate endDate;
}

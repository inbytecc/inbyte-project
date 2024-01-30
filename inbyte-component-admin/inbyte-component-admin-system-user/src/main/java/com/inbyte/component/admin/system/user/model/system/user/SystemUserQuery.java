package com.inbyte.component.admin.system.user.model.system.user;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用户表查询
 *
 * @author chenjw
 * @date 2022-12-20 14:58:26
 **/
@Getter
@Setter
public class SystemUserQuery extends BasePage {

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

    /**
     * 商户号
     **/
    private String mctNo;


}
package com.inbyte.component.admin.system.user.model.system.role;

import com.inbyte.commons.model.dto.BaseQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 角色查询
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@Getter
@Setter
public class SystemRoleQuery extends BaseQuery {

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

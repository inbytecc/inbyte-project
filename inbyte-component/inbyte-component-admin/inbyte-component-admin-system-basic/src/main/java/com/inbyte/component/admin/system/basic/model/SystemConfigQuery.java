package com.inbyte.component.admin.system.basic.model;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * 系统配置查询
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Getter
@Setter
public class SystemConfigQuery extends BasePage {

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
     * 排序字段
     **/
    @Pattern(regexp = "create_time|open", message = "排序字段不合法")
    private String orderColumn;

    /**
     * 升降序
     **/
    @Pattern(regexp = "asc|desc", message = "排序方式不合法")
    private String ordering;
}

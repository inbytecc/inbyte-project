package com.inbyte.component.admin.marketing.model.clue;

import com.inbyte.commons.model.dto.BaseQuery;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 客户线索查询
 *
 * @author chenjw
 * @date 2023-03-09 13:22:05
 **/
@Getter
@Setter
public class CustomerClueQuery extends BaseQuery {

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
     * 线索状态
     */
    private Integer clueStatus;

    /**
     * 排序字段
     **/
    @Pattern(regexp = "create_time|latest_contact_time|next_contact_time|intention_level", message = "排序字段不合法")
    private String orderColumn;
    /**
     * 升降序
     **/
    @Pattern(regexp = "asc|desc", message = "排序方式不合法")
    private String ordering;
}

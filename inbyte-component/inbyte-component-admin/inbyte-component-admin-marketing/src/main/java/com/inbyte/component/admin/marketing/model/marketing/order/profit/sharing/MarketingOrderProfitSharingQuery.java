package com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * 订单分账记录查询
 *
 * @author chenjw
 * @date 2024-11-19 16:11:50
 **/
@Getter
@Setter
public class MarketingOrderProfitSharingQuery extends BasePage {

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
    @Pattern(regexp = "create_time|update_time", message = "排序字段不合法")
    private String orderColumn;

    /**
     * 升降序
     **/
    @Pattern(regexp = "asc|desc", message = "排序方式不合法")
    private String ordering;
}

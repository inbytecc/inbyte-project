package com.inbyte.component.admin.marketing.model.qrcode;

import com.inbyte.commons.model.dto.BaseQuery;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 商户二维码查询
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@Getter
@Setter
public class QrcodeMerchantQuery extends BaseQuery {

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
    @Pattern(regexp = "invest_cost|ad_page_count|view_count|register_count|clue_count|order_count|trade_amount|create_time", message = "排序字段不合法")
    private String orderColumn;
    /**
     * 升降序
     **/
    @Pattern(regexp = "asc|desc", message = "排序方式不合法")
    private String ordering;
}

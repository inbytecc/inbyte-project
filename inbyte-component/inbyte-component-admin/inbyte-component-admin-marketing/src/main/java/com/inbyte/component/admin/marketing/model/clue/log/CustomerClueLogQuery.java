package com.inbyte.component.admin.marketing.model.clue.log;

import com.inbyte.commons.model.dto.BaseQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 跟进记录查询
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
@Getter
@Setter
public class CustomerClueLogQuery extends BaseQuery {


    /**
     * 线索ID
     **/
    private Integer clueId;

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

package com.inbyte.component.admin.user.model.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 用户定位查询
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@Getter
@Setter
public class UserLocationQuery {

    /**
     * 查询关键字
     **/
    private String keyword;
    /**
     * 商户号
     **/
    private String mctNo;

    /**
    * 活跃开始日期
    */
    private LocalDate startDate;

    /**
    * 活跃截止日期
    */
    private LocalDate endDate;

    /**
     * 精度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 公里范围
     */
    private BigDecimal range;
}

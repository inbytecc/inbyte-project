package com.inbyte.component.admin.user.model.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户定位摘要
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@Getter
@Setter
public class UserLocationBrief {

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /**
     * 分布数量
     */
    private Integer count;

}

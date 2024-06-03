package com.inbyte.component.admin.marketing.model.qrcode;

import com.inbyte.commons.model.dto.BaseQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用户定位查询
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@Getter
@Setter
public class QrcodeMerchantUserTrendQuery extends BaseQuery {


    /**
     * 宣传码ID
     */
    private Integer qcid;

    /**
     * 活跃开始日期
     */
    private LocalDate startDate;

    /**
     * 活跃截止日期
     */
    private LocalDate endDate;
}

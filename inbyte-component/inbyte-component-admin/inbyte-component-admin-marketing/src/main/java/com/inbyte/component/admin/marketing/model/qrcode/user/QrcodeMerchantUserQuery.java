package com.inbyte.component.admin.marketing.model.qrcode.user;

import com.inbyte.commons.model.dto.BaseQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 商户二维码注册用户查询
 *
 * @author chenjw
 * @date 2023-04-04 16:39:21
 **/
@Getter
@Setter
public class QrcodeMerchantUserQuery extends BaseQuery {

    /**
     * 二维码ID
     */
    private Integer qcid;

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

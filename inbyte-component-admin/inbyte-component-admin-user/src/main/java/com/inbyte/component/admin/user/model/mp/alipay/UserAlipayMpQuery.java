package com.inbyte.component.admin.user.model.mp.alipay;

import com.inbyte.commons.model.dto.BasePage;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 支付宝小程序用户查询
 *
 * @author chenjw
 * @date 2023-10-12 09:36:51
 **/
@Getter
@Setter
public class UserAlipayMpQuery extends BasePage {

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
    @Pattern(regexp = "create_time|latest_login_time|location_update_time", message = "排序字段不合法")
    private String orderColumn;
    /**
     * 升降序
     **/
    @Pattern(regexp = "asc|desc", message = "排序方式不合法")
    private String ordering;
}

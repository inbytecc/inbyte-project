package com.inbyte.component.admin.user.model.mp.weixin;

import com.inbyte.commons.model.dto.BaseQuery;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 微信小程序用户查询
 *
 * @author chenjw
 * @date 2023-03-29 13:58:35
 **/
@Getter
@Setter
public class UserWeixinMpQuery extends BaseQuery {

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
    @Pattern(regexp = "createTime|latestLoginTime|locationUpdateTime|inviteCount|loginCount", message = "排序字段不合法")
    private String orderColumn;
    /**
     * 升降序
     **/
    @Pattern(regexp = "asc|desc", message = "排序方式不合法")
    private String ordering;

    /**
     * 推荐人
     */
    private Integer recommendEid;
}

package com.inbyte.component.admin.user.model.mp.weixin;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信小程序用户实体
 *
 * @author chenjw
 * @date 2023-03-29 13:58:35
 **/
@Getter
@Setter
public class UserWeixinMpUpdate {

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 备注
     */
    private String remark;

}

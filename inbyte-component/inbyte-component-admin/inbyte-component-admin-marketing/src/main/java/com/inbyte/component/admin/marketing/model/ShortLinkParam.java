package com.inbyte.component.admin.marketing.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 短链参数
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@Getter
@Setter
public class ShortLinkParam {

    /**
     * 微信外部用户ID
     */
    private Integer eid;

    /**
     * 路径
     */
    @NotNull(message = "路径不能为空")
    private String path;

}

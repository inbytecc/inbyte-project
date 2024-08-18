package com.inbyte.component.admin.system.user.model.system.user.merchant;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户商户实体
 *
 * @author chenjw
 * @date 2024-08-18 14:34:14
 **/
@Getter
@Setter
public class InbyteSystemUserMerchantBrief {

    /**
      * 商户号
      */
    private String mctNo;

    /**
      * 商户名称
      */
    private String mctName;

    /**
      * Logo
      */
    private String logo;

}

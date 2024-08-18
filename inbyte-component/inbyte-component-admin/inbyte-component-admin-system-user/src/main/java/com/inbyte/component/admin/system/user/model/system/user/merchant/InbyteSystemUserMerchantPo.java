package com.inbyte.component.admin.system.user.model.system.user.merchant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户商户实体
 *
 * @author chenjw
 * @date 2024-08-18 14:34:14
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_system_user_merchant")
public class InbyteSystemUserMerchantPo {

    /**
      * 用户商户
      */
    @TableId(value = "mct_user_id", type = IdType.AUTO)
    private Integer mctUserId;

    /**
      * 用户ID
      */
    private Integer userId;

    /**
      * 商户号
      */
    private String mctNo;

}

package com.inbyte.component.app.user.wallet.model.user.wallet;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户钱包实体
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("user_wallet")
public class UserWalletPo {

    /**
      * 钱包ID
      */
    @TableId(value = "userId", type = IdType.INPUT)
    private Integer userId;

    /**
      * 余额
      */
    private BigDecimal balance;

    /**
      * 创建时间
      */
    private LocalDateTime createdTime;

    /**
      * 更新时间
      */
    private LocalDateTime updatedTime;

}

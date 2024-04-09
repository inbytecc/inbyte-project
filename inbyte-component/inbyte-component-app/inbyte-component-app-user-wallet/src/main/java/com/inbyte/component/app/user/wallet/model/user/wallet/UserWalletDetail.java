package com.inbyte.component.app.user.wallet.model.user.wallet;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户钱包详情
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 **/
@Getter
@Setter
public class UserWalletDetail {

    /**
     * 余额
     */
    private BigDecimal balance;

}

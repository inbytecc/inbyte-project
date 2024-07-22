package com.inbyte.component.app.user.wallet.model.user.wallet;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值配置
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 **/
@Getter
@Setter
public class UserWalletRechargeBrief {

    /** 充值ID */
    private Integer rechargeId;
    /** 售价 */
    private BigDecimal salePrice;
    /** 充值金额 */
    private BigDecimal rechargeAmount;

}

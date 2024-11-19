package com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing;

import com.inbyte.commons.model.enums.ProcessEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;


/**
 * 订单分账记录修改
 *
 * @author chenjw
 * @date 2024-11-19 16:11:50
 **/
@Getter
@Setter
public class MarketingOrderProfitSharingUpdate {

    /** 分账记录ID */
    private Integer shareLogId;

    /** 分账比例 */
    private Integer sharePercentage;

    /** 分账金额 */
    private BigDecimal shareAmount;

    /** 分账状态 */
    @Length(max = 32, message = "分账状态长度不能超过32位")
    private ProcessEnum shareStatus;

    /** 已取消 */
    private Integer canceled;

}

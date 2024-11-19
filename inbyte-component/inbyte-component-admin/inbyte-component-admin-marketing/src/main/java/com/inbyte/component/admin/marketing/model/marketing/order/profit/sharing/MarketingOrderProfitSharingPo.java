package com.inbyte.component.admin.marketing.model.marketing.order.profit.sharing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.enums.AccountTypeEnum;
import com.inbyte.commons.model.enums.ProcessEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单分账记录实体
 *
 * @author chenjw
 * @date 2024-11-19 16:11:50
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("marketing_order_profit_sharing")
public class MarketingOrderProfitSharingPo {

    /**
      * 分账记录ID
      */
    @TableId(value = "share_log_id", type = IdType.AUTO)
    private Integer shareLogId;

    /**
     * 分账编号
     */
    private String shareLogNo;

    /**
      * 订单编号
      */
    private String orderNo;

    /**
      * 订单标题
      */
    private String orderTitle;

    /**
      * 订单概要
      */
    private String orderBrief;

    /**
      * 分销员ID
      */
    private Integer distributorId;

    /**
      * 分销员类型
      */
    private AccountTypeEnum accountType;

    /**
      * 分销员名称
      */
    private String distributorName;

    /**
      * 子商户号
      */
    private String subMerchantId;

    /**
      * 分账比例
      */
    private Integer sharePercentage;

    /**
      * 分账金额
      */
    private BigDecimal shareAmount;

    /**
      * 分账状态
      */
    private ProcessEnum shareStatus;

    /**
      * 已取消
      */
    private Integer canceled;

    /**
      * 创建人
      */
    private String creator;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
      * 修改人
      */
    private String modifier;

    /**
      * 更新时间
      */
    private LocalDateTime updateTime;

    /**
      * 商户号
      */
    private String mctNo;

    /**
     * 营地ID
     */
    private String venueId;

    /**
     * 营地名
     */
    private String venueName;

    /**
      * 删除
      */
    private Integer deleted;

}

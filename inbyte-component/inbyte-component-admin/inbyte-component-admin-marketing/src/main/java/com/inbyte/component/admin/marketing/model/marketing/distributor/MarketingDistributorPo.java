package com.inbyte.component.admin.marketing.model.marketing.distributor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.enums.AccountTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 分销商实体
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("marketing_distributor")
public class MarketingDistributorPo {

    /**
      * 分销商ID
      */
    @TableId(value = "distributor_id", type = IdType.AUTO)
    private Integer distributorId;

    /**
      * 账户类型
      */
    private AccountTypeEnum accountType;

    /**
      * 分账接收方账号
      */
    private String receiverAccount;

    /**
      * 分账接收方名字
      */
    private String receiverName;

    /**
      * 小程序用户ID
      */
    private Integer eid;

    /**
      * 联系电话
      */
    private String tel;

    /**
      * 微信号
      */
    private String wechatId;

    /**
      * 逻辑删除
      */
    private Integer deleted;

    /**
      * 商户号
      */
    private String mctNo;

    /**
      * 备注
      */
    private String remark;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
      * 创建人
      */
    private String creator;

    /**
      * 更新时间
      */
    private LocalDateTime updateTime;

    /**
      * 修改人
      */
    private String modifier;

}

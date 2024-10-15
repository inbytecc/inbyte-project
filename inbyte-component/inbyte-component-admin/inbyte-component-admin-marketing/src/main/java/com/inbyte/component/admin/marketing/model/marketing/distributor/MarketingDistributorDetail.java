package com.inbyte.component.admin.marketing.model.marketing.distributor;

import com.inbyte.commons.model.enums.AccountTypeEnum;
import com.inbyte.component.common.dict.convert.DictSerialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 分销商详情
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@Getter
@Setter
public class MarketingDistributorDetail {

    /** 分销商ID */
    private Integer distributorId;

    /** 账户类型 */
    @DictSerialize(AccountTypeEnum.class)
    private AccountTypeEnum accountType;

    /** 分账接收方账号 */
    private String receiverAccount;

    /** 分账接收方名字 */
    private String receiverName;

    /** 小程序用户ID */
    private Integer eid;

    /** 联系电话 */
    private String tel;

    /** 微信号 */
    private String wechatId;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 创建人 */
    private String creator;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 修改人 */
    private String modifier;

}

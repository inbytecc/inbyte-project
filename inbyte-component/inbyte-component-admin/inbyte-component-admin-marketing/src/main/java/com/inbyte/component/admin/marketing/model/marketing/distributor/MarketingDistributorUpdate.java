package com.inbyte.component.admin.marketing.model.marketing.distributor;

import com.inbyte.commons.model.enums.AccountTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


/**
 * 分销商修改
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@Getter
@Setter
public class MarketingDistributorUpdate {

    /** 分销商ID */
    private Integer distributorId;

    /** 账户类型 */
    private AccountTypeEnum accountType;

    /** 分账接收方账号 */
    @Length(max = 128, message = "分账接收方账号长度不能超过128位")
    private String receiverAccount;

    /** 分账接收方名字 */
    @Length(max = 64, message = "分账接收方名字长度不能超过64位")
    private String receiverName;

    /** 小程序用户ID */
    private Integer eid;

    /** 联系电话 */
    @Length(max = 11, message = "联系电话长度不能超过11位")
    private String tel;

    /** 微信号 */
    @Length(max = 32, message = "微信号长度不能超过32位")
    private String wechatId;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

}

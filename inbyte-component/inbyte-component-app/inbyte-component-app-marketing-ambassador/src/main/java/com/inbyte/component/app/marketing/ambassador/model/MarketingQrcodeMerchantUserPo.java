package com.inbyte.component.app.marketing.ambassador.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商户二维码注册用户实体
 *
 * @author chenjw
 * @date 2023-03-29 16:23:42
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("marketing_qrcode_merchant_user")
public class MarketingQrcodeMerchantUserPo {

    /**
     * 注册号
     */
    @TableId(value = "qm_user_id", type = IdType.AUTO)
    private Integer qmUserId;

    /**
     * 商户二维码ID
     */
    private Integer qcid;

    /**
     * 注册用户ID
     */
    private Integer eid;

    /**
     * 用户类型
     */
    private AppTypeEnum etp;

    /**
     * 已注册
     */
    private Integer registered;

    /**
     * 已预约
     */
    private Integer appointed;

    /**
     * 已成交
     */
    private Integer madeDeal;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}

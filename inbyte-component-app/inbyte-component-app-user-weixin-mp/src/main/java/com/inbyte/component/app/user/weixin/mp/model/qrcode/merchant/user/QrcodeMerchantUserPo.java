package com.inbyte.component.app.user.weixin.mp.model.qrcode.merchant.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("qrcode_merchant_user")
public class QrcodeMerchantUserPo {

    /**
     * 注册号
     */
    @TableId(value = "qmUserId", type = IdType.AUTO)
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
    private Integer etp;

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
     * 创建时间
     */
    private LocalDateTime createTime;

}

package com.inbyte.component.admin.marketing.model.qrcode.user;

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
 * @date 2023-04-04 16:39:21
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
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 已注册
     */
    private Integer registered;

    /**
     * 已预约
     */
    private Integer madeAppointment;

    /**
     * 已成交
     */
    private Integer madeDeal;

    /**
     * 订单数
     */
    private Integer orderCount;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}

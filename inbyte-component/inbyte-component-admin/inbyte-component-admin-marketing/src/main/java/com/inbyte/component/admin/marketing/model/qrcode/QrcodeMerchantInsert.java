package com.inbyte.component.admin.marketing.model.qrcode;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 商户二维码创建
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@Getter
@Setter
public class QrcodeMerchantInsert {

    /** 名称 */
    @NotNull(message = "名称不能为空")
    @Length(max = 16, message = "名称长度不能超过16位")
    private String name;

    /** 投入成本 */
    private BigDecimal investCost;

    /** 线上广告 */
    @NotNull(message = "线上广告不能为空")
    private Integer onlineAd;

    /** 页面路径 */
    @Length(max = 255, message = "页面路径长度不能超过255位")
    private String page;

    /** 页面参数 */
    private String scene;

    /** 模块名 */
    @Length(max = 16, message = "模块名长度不能超过16位")
    private String moduleName;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

}

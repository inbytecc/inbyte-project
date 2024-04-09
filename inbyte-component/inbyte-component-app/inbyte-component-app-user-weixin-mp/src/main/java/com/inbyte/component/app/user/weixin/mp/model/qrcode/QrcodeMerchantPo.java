package com.inbyte.component.app.user.weixin.mp.model.qrcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商户二维码实体
 *
 * @author chenjw
 * @date 2023-03-20 15:07:20
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("qrcode_merchant")
public class QrcodeMerchantPo {

    /**
      * 宣传码ID
      */
    @TableId(value = "qcid", type = IdType.AUTO)
    private Integer qcid;

    /**
      * 商户ID
      */
    private String mctNo;

    /**
      * 名称
      */
    private String name;

    /**
      * 投入成本
      */
    private BigDecimal investCost;

    /**
      * 宣传单页数量
      */
    private Integer adPageCount;

    /**
      * 页面路径
      */
    private String page;

    /**
      * 页面参数
      */
    private String scene;

    /**
      * 备注
      */
    private String remark;

    /**
      * 访问次数
      */
    private Integer viewCount;

    /**
      * 注册数量
      */
    private Integer registerCount;

    /**
      * 线索数量
      */
    private Integer clueCount;

    /**
      * 订单数量
      */
    private Integer orderCount;

    /**
      * 交易总额
      */
    private BigDecimal tradeAmount;

    /**
      * 经度
      */
    private BigDecimal longitude;

    /**
      * 纬度
      */
    private BigDecimal latitude;

    /**
      * 已删除
      */
    private Integer deleted;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
      * 创建人ID
      */
    private String creatorId;

    /**
      * 创建人名字
      */
    private String creatorName;

    /**
      * 更新时间
      */
    private LocalDateTime updateTime;

}

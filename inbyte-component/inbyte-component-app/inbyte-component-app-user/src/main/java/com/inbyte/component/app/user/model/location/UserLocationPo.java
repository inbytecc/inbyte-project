package com.inbyte.component.app.user.model.location;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户定位实体
 *
 * @author chenjw
 * @date 2023-03-29 11:23:32
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("user_location")
public class UserLocationPo {

    /**
      * 定位ID
      */
    @TableId(value = "location_id", type = IdType.AUTO)
    private Integer locationId;

    /**
      * 用户ID
      */
    private Integer userId;

    /**
      * 外部用户类型
      */
    private AppTypeEnum appType;

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 商户号
     */
    private String mctNo;

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

package com.inbyte.component.common.basic.model;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 应用实体
 *
 * @author chenjw
 * @date 2024-04-11 15:53:23
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_app")
public class InbyteAppPo {

    /**
      * appId
      */
    @TableId(value = "app_id", type = IdType.AUTO)
    private String appId;

    /**
      * app名称
      */
    private String appName;

    /**
      * app类型
      */
    private String appType;

    /**
      * app密钥
      */
    private String secret;

    /**
      * 商户号
      */
    private String mctNo;

    /**
     * 扩展信息
     */
    private JSONObject extJson;

    /**
      * 备注
      */
    private String remark;

    /**
     * app类型
     */
    private Long maAuditId;

    /**
     * 小程序审核中
     */
    private Integer maUnderAudit;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}

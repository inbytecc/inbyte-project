package com.inbyte.component.common.weixin.enterprise.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 消息通知 - 企微实体
 *
 * @author chenjw
 * @date 2024-05-24 14:47:44
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_notice_enterprise_weixin")
public class InbyteNoticeEnterpriseWeixinPo {

    /**
      * appId
      */
    @TableId(value = "venue_id", type = IdType.AUTO)
    private String venueId;

    /**
      * 企微机器人ID
      */
    private String defaultRobotId;

    /**
      * 备注
      */
    private String remark;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

}

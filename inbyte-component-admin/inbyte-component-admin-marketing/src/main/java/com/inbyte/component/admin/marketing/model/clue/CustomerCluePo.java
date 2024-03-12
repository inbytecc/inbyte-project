package com.inbyte.component.admin.marketing.model.clue;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 客户线索实体
 *
 * @author chenjw
 * @date 2023-03-22 15:18:45
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("customer_clue")
public class CustomerCluePo {

    /**
     * 线索ID
     */
    @TableId(value = "clueId", type = IdType.AUTO)
    private Integer clueId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 客户名字
     */
    private String nickName;

    /**
     * 主题名称
     */
    private String subjectName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 更多信息
     */
    private Object moreInfo;

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 外部用户类型
     */
    private Integer etp;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 来源路径
     */
    private String cluePath;

    /**
     * 来源路径参数
     */
    private String cluePathParam;

    /**
     * 来源类型
     */
    private Integer sourceType;

    /**
     * 意向级别
     */
    private Integer intentionLevel;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 已分配
     */
    private Integer dispatched;

    /**
     * 跟进人ID
     */
    private Integer contactPersonId;

    /**
     * 跟进人名字
     */
    private String contactPersonName;

    /**
     * 跟进阶段
     */
    private Integer contactStage;

    /**
     * 跟进中
     */
    private Integer contacting;

    /**
     * 最近跟进时间
     */
    private LocalDateTime latestContactTime;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextContactTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 已预约体验
     */
    private Integer appointment;

    /**
     * 预约时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 已体验
     */
    private Integer trialed;

    /**
     * 体验时间
     */
    private LocalDateTime trailTime;

    /**
     * 放弃客户
     */
    private Integer dropped;

    /**
     * 放弃原因
     */
    private String dropReason;

    /**
     * 放弃时间
     */
    private LocalDateTime dropTime;

    /**
     * 已转化
     */
    private Integer converted;

    /**
     * 转化时间
     */
    private LocalDateTime convertTime;

    /**
     * 已删除
     */
    private Integer deleted;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 最近跟进内容
     */
    private String latestContactMsg;

    /**
     * 推荐人ID
     */
    private Integer recommendEid;

    /**
     * 推荐人名字
     */
    private String recommendUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Integer creatorId;

    /**
     * 创建人名字
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
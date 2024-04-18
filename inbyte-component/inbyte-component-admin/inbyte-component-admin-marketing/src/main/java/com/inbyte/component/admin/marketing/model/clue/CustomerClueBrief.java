package com.inbyte.component.admin.marketing.model.clue;

import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 客户线索摘要
 *
 * @author chenjw
 * @date 2023-03-09 13:22:05
 **/
@Getter
@Setter
public class CustomerClueBrief {

    /**
     * 线索ID
     */
    private Integer clueId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 客户名字
     */
    private String nickname;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 学生名字
     */
    private String studentName;

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 外部用户类型
     */
    private AppTypeEnum etp;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 主题名称
     */
    private String subjectName;

    /**
     * 模块路径
     */
    private String moduleName;

    /**
     * 更多信息
     */
    private String moreInfo;

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
     * 已分配
     */
    private Integer dispatched;

    /**
     * 跟进人ID
     */
    private String contactPersonId;

    /**
     * 跟进人名字
     */
    private String contactPersonName;

    /**
     * 跟进中
     */
    private Integer contacting;

    /**
     * 跟进阶段
     */
    private Integer contactStage;

    /**
     * 最近跟进时间
     */
    private LocalDateTime latestContactTime;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextContactTime;

    /**
     * 推荐人ID
     */
    private Integer recommendEid;

    /**
     * 推荐人名字
     */
    private String recommendUserName;

    /**
     * 放弃客户
     */
    private Integer dropped;

    /**
     * 放弃原因
     */
    private String dropReason;

    /**
     * 已转化
     */
    private Integer converted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最近跟进内容
     */
    private String latestContactMsg;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

}

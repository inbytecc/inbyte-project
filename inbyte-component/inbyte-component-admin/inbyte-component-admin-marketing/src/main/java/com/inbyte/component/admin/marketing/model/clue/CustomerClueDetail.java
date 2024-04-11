package com.inbyte.component.admin.marketing.model.clue;

import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 客户线索详情
 *
 * @author chenjw
 * @date 2023-03-09 13:22:05
 **/
@Getter
@Setter
public class CustomerClueDetail {

    /** 线索ID */
    private Integer clueId;

    /** 用户ID */
    private Integer userId;

    /** 客户名字 */
    private String nickName;

    /** 外部用户ID */
    private Integer eid;

    /** 外部用户类型 */
    private AppTypeEnum etp;

    /** 手机号 */
    private String tel;

    /** 来源路径 */
    private String cluePath;

    /** 来源路径参数 */
    private String cluePathParam;

    /**
     * 更多信息
     */
    private JSONObject moreInfo;

    /** 来源类型 */
    private Integer sourceType;

    /** 意向级别 */
    private Integer intentionLevel;

    /** 已分配 */
    private Integer dispatched;

    /** 跟进人ID */
    private String contactPersonId;

    /** 跟进人名字 */
    private String contactPersonName;

    /** 最近跟进时间 */
    private LocalDateTime latestContactTime;

    /** 下次跟进时间 */
    private LocalDateTime nextContactTime;

    /** 放弃客户 */
    private Integer dropped;

    /** 放弃原因 */
    private String dropReason;

    /** 已转化 */
    private Integer converted;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 创建人ID */
    private Integer creatorId;

    /** 创建人名字 */
    private String creatorName;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

}

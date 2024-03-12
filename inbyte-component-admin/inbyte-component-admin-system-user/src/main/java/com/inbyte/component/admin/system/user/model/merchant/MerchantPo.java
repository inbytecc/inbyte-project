package com.inbyte.component.admin.system.user.model.merchant;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 商户实体
 *
 * @author chenjw
 * @date 2023-11-05 12:29:19
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("merchant")
public class MerchantPo {

    /**
     * 商户号
     */
    @TableId(value = "mct_no", type = IdType.AUTO)
    private String mctNo;

    /**
     * 商户名
     */
    private String mctName;

    /**
     * 拼音名字
     */
    private String pinYinName;

    /**
     * 文件数
     */
    private Integer fileCount;

    /**
     * 文件总大小
     */
    private Integer fileSizeCount;

    /**
     * 首页配置
     */
    private JSONArray homePageConfig;

    /**
     * 加入我们
     */
    private JSONArray joinUs;

    /**
     * 黄金广告位
     */
    private JSONArray goldenBanner;

    /**
     * 公司介绍
     */
    private JSONArray companyIntroduce;

    /**
     * 关于我们
     */
    private JSONArray aboutUs;

    /**
     * 销售合伙人
     */
    private JSONArray salesPartner;

    /**
     * 技术合伙人
     */
    private JSONArray itPartner;

    /**
     * 技术共创
     */
    private JSONArray itEntrepreneurship;

    /**
     * Boss直聊
     */
    private JSONArray chatToBoss;

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

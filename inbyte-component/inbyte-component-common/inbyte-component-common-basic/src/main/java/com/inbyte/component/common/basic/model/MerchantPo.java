package com.inbyte.component.common.basic.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 商户实体
 *
 * @author chenjw
 * @date 2024-03-11 17:14:03
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_merchant")
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
    private String pinyinName;

    /**
     * 文件数
     */
    private Integer fileCount;

    /**
     * 文件总大小
     */
    private Integer fileSizeCount;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private String modifier;


}

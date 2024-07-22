package com.inbyte.component.admin.system.dict.model.dict.item;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典项实体
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_dict_item")
public class DictItemPo {

    /**
     * 条目ID
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    /**
     * 字典ID
     */
    private Integer dictId;

    /**
     * 父节点
     */
    private Integer parentId;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 名字
     */
    private String name;

    /**
     * ICON
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private BigDecimal ordinal;

    /**
     * 删除
     */
    private Integer deleted;

    /**
     * 日期
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 商户号
     */
    private String mctNo;

}

package com.inbyte.component.admin.dict.model.dict.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 字典项详情
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Getter
@Setter
public class DictItemDetail {

    /** 条目ID */
    private Integer itemId;

    /** 字典ID */
    private Integer dictId;

    /** 字典编码 */
    private String code;

    /** 名字 */
    private String name;

    /** 备注 */
    private String remark;

    /** 日期 */
    private LocalDateTime createTime;

    /** 创建人 */
    private String creator;

    /** 更新日期 */
    private LocalDateTime updateTime;

    /** 修改人 */
    private String modifier;

}

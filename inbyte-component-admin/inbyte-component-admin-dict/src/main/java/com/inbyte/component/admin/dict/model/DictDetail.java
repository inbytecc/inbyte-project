package com.inbyte.component.admin.dict.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 字典详情
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Getter
@Setter
public class DictDetail {

    /** ID */
    private Integer dictId;

    /** 字典名字 */
    private String dictName;

    /** 名字 */
    private String name;

    /** 标签名 */
    private String label;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 创建人ID */
    private Integer createUserId;

    /** 创建人名字 */
    private String createUserName;

    /** 更新时间 */
    private LocalDateTime updateTime;

}

package com.inbyte.component.admin.system.user.model.system.role;

import com.alibaba.fastjson2.JSONArray;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;

/**
 * 角色创建
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@Getter
@Setter
public class SystemRoleInsert {

    /** 名称 */
    @Length(max = 128, message = "名称长度不能超过128位")
    private String name;

    /** 菜单 */
    private JSONArray menu;

    /** 备注 */
    @Length(max = 256, message = "备注长度不能超过256位")
    private String remark;

}

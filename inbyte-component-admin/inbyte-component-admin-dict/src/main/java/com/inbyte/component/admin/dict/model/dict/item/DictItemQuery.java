package com.inbyte.component.admin.dict.model.dict.item;

import com.inbyte.commons.model.dto.BasePage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典项查询
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Getter
@Setter
public class DictItemQuery extends BasePage {

    /** 字典ID */
    @NotNull(message = "字典ID不能为空")
    private Integer dictId;

    /**
     * 查询关键字
     **/
    private String keyword;

}

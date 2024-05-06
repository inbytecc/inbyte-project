package com.inbyte.component.admin.system.dict.model;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典查询
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Getter
@Setter
public class DictQuery extends BasePage {

    /**
     * 查询关键字
     **/
    private String keyword;

}

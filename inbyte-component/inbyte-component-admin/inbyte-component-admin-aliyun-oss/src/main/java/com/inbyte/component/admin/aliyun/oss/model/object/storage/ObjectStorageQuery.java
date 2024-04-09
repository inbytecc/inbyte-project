package com.inbyte.component.admin.aliyun.oss.model.object.storage;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 对象存储查询
 *
 * @author chenjw
 * @date 2023-03-14 14:35:14
 **/
@Getter
@Setter
public class ObjectStorageQuery extends BasePage {

    /**
     * 查询关键字
     **/
    private String keyword;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 截止日期
     */
    private LocalDate endDate;
}

package com.inbyte.component.iot.bisenpark.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页响应
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class PageResponse<T> {

    /**
     * 页数
     */
    private Integer pages;

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 查询数据
     */
    private List<T> records;

    /**
     * 搜索数量
     */
    private Boolean searchCount;
} 
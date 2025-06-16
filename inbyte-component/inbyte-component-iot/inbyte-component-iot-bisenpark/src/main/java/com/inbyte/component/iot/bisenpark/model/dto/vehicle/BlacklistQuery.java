package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 黑名单查询请求
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
@Builder
public class BlacklistQuery {

    /**
     * 百胜云台停车场编号
     */
    private String parkCode;

    /**
     * 页码
     */
    private Integer current;

    /**
     * 页数
     */
    private Integer size;

    /**
     * 车牌号
     */
    private String plateNum;
} 
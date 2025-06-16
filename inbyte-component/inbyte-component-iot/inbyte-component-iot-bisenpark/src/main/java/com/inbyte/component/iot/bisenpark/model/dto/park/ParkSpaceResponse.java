package com.inbyte.component.iot.bisenpark.model.dto.park;

import lombok.Getter;
import lombok.Setter;

/**
 * 停车场车位信息响应
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class ParkSpaceResponse {

    /**
     * 百胜云台停车场唯一标识
     */
    private Integer parkingId;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 停车场总车位
     */
    private String allCarport;

    /**
     * 停车场剩余车位
     */
    private String freeCarport;
} 
package com.inbyte.component.iot.bisenpark.model.dto.park;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 停车场车位信息请求参数
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
@Builder
public class ParkSpaceRequest {

    /**
     * 百胜云平台停车场唯一标识
     */
    private Integer parkingId;

    /**
     * 请求时间戳
     */
    private String timestamp;

    /**
     * 创建请求参数
     */
    public static ParkSpaceRequest create(Integer parkingId) {
        return ParkSpaceRequest.builder()
                .parkingId(parkingId)
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .build();
    }
} 
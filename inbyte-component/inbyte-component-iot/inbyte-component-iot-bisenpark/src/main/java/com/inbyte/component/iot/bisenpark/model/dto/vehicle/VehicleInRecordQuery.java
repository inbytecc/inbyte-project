package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 车辆在场记录查询请求
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
@Builder
public class VehicleInRecordQuery {

    /**
     * 百胜云台停车场唯一标识
     */
    private Integer parkingId;

    /**
     * 车辆车牌号码
     */
    private String plateNum;

    /**
     * 车辆入场开始时间
     */
    private String startInTime;

    /**
     * 车辆入场结束时间
     */
    private String endInTime;

    /**
     * 页数
     */
    private Integer current;

    /**
     * 每页条数（最大值100）
     */
    private Integer size;
} 
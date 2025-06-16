package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Getter;
import lombok.Setter;

/**
 * 车辆在场记录详情
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class VehicleInRecordDetail {

    /**
     * 数据记录唯一标识
     */
    private Integer presenceId;

    /**
     * 停车行为id和出入场行为id对应
     */
    private Long parkingActId;

    /**
     * 百胜云台停车场唯一标识
     */
    private Integer parkingId;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 车辆车牌号码
     */
    private String plateNum;

    /**
     * 车辆进场时间
     */
    private String inTime;

    /**
     * 车辆入场图片（AliOSS图片路径）
     */
    private String inImage;

    /**
     * 入场通道
     */
    private String inChannel;
} 
package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Getter;
import lombok.Setter;

/**
 * 车辆通道识别数据推送
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class VehicleDiscernPushData {

    /**
     * 百胜云台停车场唯一标识
     */
    private Integer parkingId;

    /**
     * 停车场通道Id
     */
    private String laneId;

    /**
     * 车辆车牌号码
     */
    private String plateNum;

    /**
     * 经过类型1入 2出
     */
    private Integer passType;

    /**
     * 记录Id
     */
    private Long recordId;

    /**
     * 车辆订单Id（支付订单）
     */
    private String orderId;

    /**
     * 进入时间
     */
    private String inTime;

    /**
     * 入场车牌图片地址（AliOSS图片路径）
     */
    private String inPic;

    /**
     * 出场时间
     */
    private String outTime;

    /**
     * 出场车牌图片地址（AliOSS图片路径）
     */
    private String outPic;

    /**
     * 停车时长
     */
    private String duration;

    /**
     * 费用(分)
     */
    private Integer cost;

    /**
     * 出场图片地址（AliOSS图片路径）
     */
    private String outCarPic;
} 
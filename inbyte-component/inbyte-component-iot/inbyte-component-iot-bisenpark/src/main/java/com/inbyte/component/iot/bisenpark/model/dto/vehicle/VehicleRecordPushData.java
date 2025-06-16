package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Getter;
import lombok.Setter;

/**
 * 车辆出入场数据推送
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class VehicleRecordPushData {

    /**
     * 数据记录唯一标识
     */
    private Long recordId;

    /**
     * 百胜云台停车场唯一标识
     */
    private Integer parkingId;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 停车场剩余车位
     */
    private Integer freespace;

    /**
     * 车辆车牌号码
     */
    private String plateNum;

    /**
     * 出入类型 1进 2出
     */
    private String passType;

    /**
     * 车辆进场时间
     */
    private String inTime;

    /**
     * 车辆入场图片（AliOSS图片路径）
     */
    private String inImage;

    /**
     * 车辆出场时间
     */
    private String outTime;

    /**
     * 车辆出场图片（AliOSS图片路径）
     */
    private String outImage;

    /**
     * 停车总金额
     */
    private String charge;

    /**
     * 车辆车牌颜色
     */
    private String carColor;

    /**
     * 车辆类型
     */
    private String carType;

    /**
     * 入场通道
     */
    private String inChannel;

    /**
     * 入场抬杆类型
     */
    private String inOpenGateMode;

    /**
     * 出场通道
     */
    private String outChannel;

    /**
     * 出场抬杆类型
     */
    private String outOpenGateMode;

    /**
     * 停车场编号
     */
    private String parkCode;

    /**
     * 入场通道id
     */
    private String inChannelId;

    /**
     * 出场通道id
     */
    private String outChannelId;

    /**
     * 发送数据时的时间戳
     */
    private String timestamp;

    /**
     * 昌通码ID
     */
    private Integer scanningId;
} 
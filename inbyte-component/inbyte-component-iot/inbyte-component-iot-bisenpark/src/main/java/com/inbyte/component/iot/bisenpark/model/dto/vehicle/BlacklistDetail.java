package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Getter;
import lombok.Setter;

/**
 * 黑名单详情
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class BlacklistDetail {

    /**
     * 黑名单id
     */
    private Integer id;

    /**
     * 百胜云台停车场唯一标识
     */
    private Integer parkId;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 车辆车牌号码
     */
    private String plateNum;

    /**
     * 名单类型
     */
    private Integer status;

    /**
     * 备注
     */
    private String remake;
} 
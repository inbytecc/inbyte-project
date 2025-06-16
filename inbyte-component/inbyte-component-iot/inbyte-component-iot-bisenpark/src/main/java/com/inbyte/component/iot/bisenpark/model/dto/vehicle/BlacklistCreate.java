package com.inbyte.component.iot.bisenpark.model.dto.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 黑名单新增请求
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
@Builder
public class BlacklistCreate {

    /**
     * 百胜云台停车场编号
     */
    private String parkCode;

    /**
     * 车辆车牌号码
     */
    private String plateNum;

    /**
     * 备注
     */
    private String remark;
} 
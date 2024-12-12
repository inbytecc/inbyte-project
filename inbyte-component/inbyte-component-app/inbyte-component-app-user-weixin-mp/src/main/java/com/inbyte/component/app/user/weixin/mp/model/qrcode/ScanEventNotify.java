package com.inbyte.component.app.user.weixin.mp.model.qrcode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inbyte.commons.model.enums.AppTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 扫码事件通知参数
 *
 * @author chenjw
 * @date 2022-11-28 14:42:12
 **/
@Getter
@Setter
public class ScanEventNotify {

    /** 二维码ID */
    @NotNull
    private Integer q;
    /** 二维码类型 */
    @NotNull
    private Integer t;
    /**
     * 外部用户ID
     */
    @NotNull
    private Integer shareEid;

    /**
     * 当前用户EID
     */
    @JsonIgnore
    private Integer currentEid;

    /**
     * App类型
     */
    @JsonIgnore
    private AppTypeEnum appType;
}
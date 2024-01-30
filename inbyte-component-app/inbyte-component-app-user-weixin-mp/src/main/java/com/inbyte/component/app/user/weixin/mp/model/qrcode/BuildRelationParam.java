package com.inbyte.component.app.user.weixin.mp.model.qrcode;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 扫码事件通知参数
 *
 * @author chenjw
 * @date 2022-11-28 14:42:12
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildRelationParam {

    /** 二维码ID */
    @NotNull
    private Integer qcid;
    /**
     * 二维码类型
     */
    @NotNull
    private Integer qctp;
    /**
     * 外部用户ID
     */
    @NotNull
    private Integer eid;
    /**
     * 外部用户ID类型
     */
    @NotNull
    private Integer etp;
    /**
     * 外部用户ID
     */
    @NotNull
    private Integer recommendEid;

}
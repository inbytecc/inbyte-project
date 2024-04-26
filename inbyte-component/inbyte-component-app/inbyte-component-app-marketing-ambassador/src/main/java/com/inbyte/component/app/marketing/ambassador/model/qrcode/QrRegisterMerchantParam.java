package com.inbyte.component.app.marketing.ambassador.model.qrcode;

import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.*;

/**
 * 二维码分享注册通知
 *
 * @author chenjw
 * @date 2022-11-28 14:42:12
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrRegisterMerchantParam {

    /** 二维码ID */
    private Integer q;
    /** 二维码类型 */
    private Integer t;
    /**
     * 注册人外部用户ID
     */
    private Integer eid;
    /**
     * 注册人外部用户类型
     */
    private AppTypeEnum etp;
}
package com.inbyte.component.app.sign.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.sign.model.AppSignDto;
import com.inbyte.component.app.sign.model.AppSignParam;

/**
 * 应用服务
 *
 * @author chenjw
 * @date: 2022/12/31
 */
public interface AppSignService {


    /**
     * App 签名
     * @param param
     * @return
     */
    R<AppSignDto> appSign(AppSignParam param);


}

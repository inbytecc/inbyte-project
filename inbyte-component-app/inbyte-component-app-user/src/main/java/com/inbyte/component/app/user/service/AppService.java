package com.inbyte.component.app.user.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.model.app.AppSignDto;
import com.inbyte.component.app.user.model.app.AppSignParam;

/**
 * 应用服务
 *
 * @author chenjw
 * @date: 2022/12/31
 */
public interface AppService {


    /**
     * App 签名
     * @param param
     * @return
     */
    R<AppSignDto> appSign(AppSignParam param);


}

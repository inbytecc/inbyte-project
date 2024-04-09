package com.inbyte.component.app.user.weixin.mp.service;

import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;

/**
 * 短链服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
public interface WeixinMpLinkService {

    R<String> getUrlLink(BasePath basePath);

    R<String> getShortLink(BasePath basePath);
}
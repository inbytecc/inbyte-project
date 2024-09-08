package com.inbyte.component.admin.marketing.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.ShortLinkParam;

/**
 * 短链服务
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
public interface ShortLinkService {

    R<String> getShortLink(ShortLinkParam param);
}

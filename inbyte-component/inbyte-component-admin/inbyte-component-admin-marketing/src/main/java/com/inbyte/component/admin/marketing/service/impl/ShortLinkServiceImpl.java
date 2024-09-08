package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.enums.AppTypeEnum;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.admin.marketing.model.ShortLinkParam;
import com.inbyte.component.admin.marketing.service.ShortLinkService;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.common.basic.dao.InbyteAppMapper;
import com.inbyte.component.common.basic.model.InbyteAppPo;
import com.inbyte.util.weixin.mp.client.WxMpLinkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户二维码服务
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    @Autowired
    private InbyteAppMapper appMapper;
    @Autowired
    private WxMpLinkClient wxMpLinkClient;

    @Override
    public R<String> getShortLink(ShortLinkParam param) {
        if (param.getEid() != null) {
            if (param.getPath().contains("?")) {
                param.setPath(param.getPath() + "&s=" + param.getEid());
            } else {
                param.setPath(param.getPath() + "?s=" + param.getEid());
            }
        }

        return wxMpLinkClient.generateShortLink(getAppId(),
                param.getPath(),
                "",
                WhetherDict.Yes);
    }

    private String getAppId() {
        InbyteAppPo inbyteAppPo = appMapper.selectOne(new LambdaQueryWrapper<InbyteAppPo>()
                .eq(InbyteAppPo::getAppType, AppTypeEnum.WXMP)
                .eq(InbyteAppPo::getMctNo, SessionUtil.getMctNo())
                .last("limit 1"));
        if (inbyteAppPo == null) {
            throw InbyteException.failure("当前商户未配置微信小程序");
        }
        if (StringUtil.isEmpty(inbyteAppPo.getAppId())) {
            throw InbyteException.failure("当前商户微信小程序信息未完善");
        }
        return inbyteAppPo.getAppId();
    }

}

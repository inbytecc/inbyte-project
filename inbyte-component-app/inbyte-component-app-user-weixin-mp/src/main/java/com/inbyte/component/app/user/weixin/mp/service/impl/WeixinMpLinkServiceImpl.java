package com.inbyte.component.app.user.weixin.mp.service.impl;

import com.inbyte.component.app.user.weixin.mp.SceneUtil;
import com.inbyte.component.app.user.weixin.mp.service.WeixinMpLinkService;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.framework.AppUtil;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.util.weixin.mp.client.WxMpLinkClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信小程序短链服务
 *
 * @author chenjw
 * @date 2023-12-13 16:55:46
 **/
@Slf4j
@Service
public class WeixinMpLinkServiceImpl implements WeixinMpLinkService {

    @Autowired
    private WxMpLinkClient wxMpLinkClient;

    @Override
    public R<String> getUrlLink(BasePath basePath) {
        String userShareScene = SceneUtil.getUserShareScene(SessionUtil.getEid(), basePath.getPathParam());
        return wxMpLinkClient.generateUrlLink(AppUtil.getAppId(),
                basePath.getPath(),
                userShareScene);
    }

    @Override
    public R<String> getShortLink(BasePath basePath) {
        String userShareScene = SceneUtil.getUserShareScene(SessionUtil.getEid(), basePath.getPathParam());
        return wxMpLinkClient.generateShortLink(AppUtil.getAppId(),
                basePath.getPath() + "?" + userShareScene,
                "",
                WhetherDict.No);
    }
}

package com.inbyte.component.app.user.weixin.mp.service.impl;

import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.sign.framework.AppUtil;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.weixin.mp.service.WeixinMpLinkService;
import com.inbyte.component.app.user.weixin.mp.util.SceneUtil;
import com.inbyte.util.weixin.mp.client.WxLinkClient;
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
    private WxLinkClient wxLinkMpClient;

    @Override
    public R<String> getUrlLink(BasePath basePath) {
        String userShareScene = SceneUtil.getUserShareScene(SessionUtil.getEid(), basePath.getPathParam());
        String urlLink = wxLinkMpClient.generateUrlLink(AppUtil.getAppId(),
                basePath.getPath(),
                userShareScene);
        return R.ok(urlLink);
    }

    @Override
    public R<String> getShortLink(BasePath basePath) {
        String userShareScene = SceneUtil.getUserShareScene(SessionUtil.getEid(), basePath.getPathParam());
        String shortLink = wxLinkMpClient.generateShortLink(AppUtil.getAppId(),
                basePath.getPath() + "?" + userShareScene,
                "",
                WhetherDict.No);
        return R.ok(shortLink);
    }
}

package com.inbyte.component.admin.system.user.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;
import com.inbyte.component.admin.system.user.model.WxMpRegisterParam;
import com.inbyte.component.admin.system.user.model.WxMpSilentLoginParam;

/**
 * 商户用户微信服务
 *
 * @author : chenjw
 * @date: 2022/11/01
 **/
public interface SystemUserWeixinMpService {

    /**
     * 微信登录
     * @param param
     * @return
     */
    R<SystemUserLoginDto> weiXinLogin(WxMpSilentLoginParam param);

    /**
     * 微信注册
     * @param param
     * @return
     */
    R<SystemUserLoginDto> weixinRegister(WxMpRegisterParam param);

}

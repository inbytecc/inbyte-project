package com.inbyte.component.app.user.weixin.mp.service;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.model.LocationUpdate;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinDto;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpUpdate;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinRegisterParam;
import com.inbyte.component.app.user.weixin.mp.model.WeixinLoginParam;
import com.inbyte.component.app.user.weixin.mp.model.UserInviteDto;
import com.inbyte.component.app.user.weixin.mp.model.UserLoginDto;
import com.inbyte.commons.model.dto.BasePage;

import java.util.List;

/**
 * 支付宝小程序用户服务
 *
 * @author chenjw
 * @date 2023-09-15 16:55:46
 **/
public interface UserWeixinMpService {

    /**
     * 微信登录
     *
     * @param param
     * @return
     */
    R<UserLoginDto> login(WeixinLoginParam param);

    /**
     * 微信注册
     *
     * @param param
     * @return
     */
    R<UserLoginDto> register(UserWeixinRegisterParam param);

    /**
     * 获取个人资料
     *
     * @return 个人资料
     */
    R<UserWeixinDto> info();

    R<String> update(UserWeixinMpUpdate userWeixinMpUpdate);

    R updateLocation(LocationUpdate locationUpdate);

    R count();

    R<List<UserInviteDto>> list(BasePage basePage);

}

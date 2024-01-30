package com.inbyte.component.admin.system.user.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dao.SystemUserMapper;
import com.inbyte.component.admin.system.user.model.WxMpRegisterParam;
import com.inbyte.component.admin.system.user.model.WxMpSilentLoginParam;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserDetail;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserPo;
import com.inbyte.component.admin.system.user.service.SystemUserWeixinMpService;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.util.weixin.mp.client.WxMpUserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 平台用户服务
 *
 * @author chenjw
 * @date 2022/12/20
 */
@Slf4j
@Service
public class SystemUserWeixinMpServiceImpl implements SystemUserWeixinMpService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private WxMpUserClient wxMpUserClient;

    @Override
    public R<SystemUserLoginDto> weiXinLogin(WxMpSilentLoginParam param) {
        SessionUser sessionUser = new SessionUser();
        R<WxMaJscode2SessionResult> weixinUserCredentialR = wxMpUserClient.code2session(
                "wxed6f47229731b642", param.getOpenIdJsCode());
        if (weixinUserCredentialR.failed()) {
            return R.valueOf(weixinUserCredentialR);
        }
        String openId = weixinUserCredentialR.getData().getOpenid();
        SystemUserDetail detail = systemUserMapper.queryByOpenId(openId);

        // 如果微信用户未创建, 新增基本信息, 并且提示注册
        if (detail == null) {
            sessionUser.setOpenId(openId);
            return R.set(ResultStatus.Unregistered, null,
                    new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
        }

        sessionUser.setUserId(detail.getUserId());
        sessionUser.setUserName(detail.getUserName());
        sessionUser.setTel(detail.getTel());
        sessionUser.setMctNo(detail.getMctNo());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        return R.success(new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
    }

    @Override
    public R<SystemUserLoginDto> weixinRegister(WxMpRegisterParam param) {
        R<WxMaPhoneNumberInfo> phoneInfo = wxMpUserClient.getPhoneInfo("wxed6f47229731b642", param.getPhoneNumberJsCode());
        if (phoneInfo.failed()) {
            return R.valueOf(phoneInfo);
        }

        SystemUserDetail detail = systemUserMapper.queryByTel(phoneInfo.getData().getPurePhoneNumber());
        if (detail == null) {
            return R.failure("未注册账号, 请联系管理员操作");
        }

        SessionUser sessionUser = SessionUtil.getSessionUserUnchecked();
        if (sessionUser == null) {
            return R.failure("请先静默登录获取token后再绑定账号");
        }

        SystemUserPo systemUserPo = SystemUserPo.builder()
                .userId(detail.getUserId())
                .openId(sessionUser.getOpenId())
                .loginWay("wx-mp")
                .latestLoginTime(LocalDateTime.now())
                .build();
        systemUserMapper.updateById(systemUserPo);

        sessionUser.setUserId(detail.getUserId());
        sessionUser.setOpenId(sessionUser.getOpenId());
        sessionUser.setUserName(detail.getUserName());
        sessionUser.setTel(detail.getTel());
        sessionUser.setMctNo(detail.getMctNo());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        return R.success(new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
    }

}

package com.inbyte.component.admin.system.user.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dao.InbyteSystemUserMapper;
import com.inbyte.component.admin.system.user.model.WxMpRegisterParam;
import com.inbyte.component.admin.system.user.model.WxMpSilentLoginParam;
import com.inbyte.component.admin.system.user.model.system.user.InbyteSystemUserPo;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserDetail;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;
import com.inbyte.component.admin.system.user.service.SystemUserWeixinMpService;
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
    private InbyteSystemUserMapper inbyteSystemUserMapper;
    @Autowired
    private WxMpUserClient wxMpUserClient;

    @Override
    public R<SystemUserLoginDto> weiXinLogin(WxMpSilentLoginParam param) {
        SessionUser sessionUser = new SessionUser();
        R<WxMaJscode2SessionResult> weixinUserCredentialR = wxMpUserClient.code2session(
                "wxc257a1d131f3b26e", param.getOpenIdJsCode());
        if (weixinUserCredentialR.failed()) {
            return R.valueOf(weixinUserCredentialR);
        }
        String openId = weixinUserCredentialR.getData().getOpenid();
        SystemUserDetail detail = inbyteSystemUserMapper.queryByOpenId(openId);

        // 如果微信用户未创建, 新增基本信息, 并且提示注册
        if (detail == null) {
            sessionUser.setOpenId(openId);
            return R.set(ResultStatus.Unregistered, null,
                    new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
        }

        inbyteSystemUserMapper.updateById(InbyteSystemUserPo.builder()
                .userId(detail.getUserId())
                .latestLoginTime(LocalDateTime.now())
                .loginWay("wx-mp")
                .build());

        sessionUser.setUserId(detail.getUserId());
        sessionUser.setUserName(detail.getUserName());
        sessionUser.setTel(detail.getTel());
        sessionUser.setMctNo(detail.getMctNo());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        sessionUser.setLoginWay("wx-mp");
        return R.ok(new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
    }

    @Override
    public R<SystemUserLoginDto> weixinRegister(WxMpRegisterParam param) {
        R<WxMaPhoneNumberInfo> phoneInfo = wxMpUserClient.getPhoneInfo("wxc257a1d131f3b26e", param.getPhoneNumberJsCode());
        if (phoneInfo.failed()) {
            return R.valueOf(phoneInfo);
        }

        SessionUser sessionUser = SessionUtil.getSessionUserUnchecked();
        if (sessionUser == null) {
            return R.failure("请先静默登录获取token后再绑定账号");
        }

        String tel = phoneInfo.getData().getPurePhoneNumber();
        SystemUserDetail detail = inbyteSystemUserMapper.queryByTel(tel);
        if (detail == null) {
            return register(tel, sessionUser);
        }

        InbyteSystemUserPo inbyteSystemUserPo = InbyteSystemUserPo.builder()
                .userId(detail.getUserId())
                .openId(sessionUser.getOpenId())
                .loginWay("wx-mp")
                .latestLoginTime(LocalDateTime.now())
                .build();
        inbyteSystemUserMapper.updateById(inbyteSystemUserPo);

        sessionUser.setUserId(detail.getUserId());
        sessionUser.setOpenId(sessionUser.getOpenId());
        sessionUser.setUserName(detail.getUserName());
        sessionUser.setTel(detail.getTel());
        sessionUser.setMctNo(detail.getMctNo());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        return R.ok(new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
    }

    /**
     * 注册账号
     */
    public R<SystemUserLoginDto> register(String tel, SessionUser sessionUser) {
        InbyteSystemUserPo inbyteSystemUserPo = InbyteSystemUserPo.builder()
                .userName(tel)
                .nickname(tel)
                .tel(tel)
                .openId(sessionUser.getOpenId())
                .loginWay("wx-mp")
                .latestLoginTime(LocalDateTime.now())
                .build();
        inbyteSystemUserMapper.insert(inbyteSystemUserPo);
        sessionUser.setUserId(inbyteSystemUserPo.getUserId());
        sessionUser.setOpenId(sessionUser.getOpenId());
        sessionUser.setUserName(inbyteSystemUserPo.getUserName());
        sessionUser.setTel(inbyteSystemUserPo.getTel());
        sessionUser.setMctNo(inbyteSystemUserPo.getMctNo());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        return R.ok(new SystemUserLoginDto(SessionUtil.getJwtToken(sessionUser)));
    }

}

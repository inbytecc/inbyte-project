package com.inbyte.component.admin.system.user.service.impl;

import com.inbyte.component.admin.system.user.dao.SystemUserMapper;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;
import com.inbyte.component.admin.system.user.service.SystemUsertWeixinEnterpriseService;
import com.inbyte.commons.model.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 平台用户服务
 *
 * @author chenjw
 * @date 2022/12/20
 */
@Slf4j
@Service
public class SystemUsertWeixinEnterpriseServiceImpl implements SystemUsertWeixinEnterpriseService {

    /**
     * 平台用户默认密码
     */
    private static final String Initial_Password = "e10adc3949ba59abbe56e057f20f883e";

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public R<SystemUserLoginDto> enterpriseWeixinOAuthLogin(String authCode) {
//        Result<QrCodeAuthEnterpriseUserDto> authResult = enterpriseWeixinUserService.getEnterpriseWeixinUserId(authCode);
//        if (authResult.failed()) {
//            return Result.valueOf(authResult);
//        }
//
//        SessionUser sessionUser = new SessionUser();
//        LocalDateTime now = LocalDateTime.now();
//        String userId = authResult.getData().getUserid();
//
//        UserPlatformDetail detail = userPlatformMapper.detail(userId);
//        if (detail != null) {
//            UserPlatformPo platformPo = UserPlatformPo.builder()
//                    .userId(detail.getUserId())
//                    .latestLoginTime(LocalDateTime.now())
//                    .loginWay("weixin-oauth")
//                    .build();
//            userPlatformMapper.updateById(platformPo);
//
//            sessionUser.setUserId(detail.getUserId());
//            sessionUser.setUserName(detail.getUserName());
//            sessionUser.setTel(detail.getTel());
//            sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
//            sessionUser.setLoginTime(now);
//            String jwt = PlatformJwtUtil.createJwt(sessionUser);
//            return Result.success(new PlatformUserLoginDto(jwt));
//        }
//        // 获取用户信息, 需要授权部分, 用户手机号, 头像, 邮箱
//        Result<EnterpriseUserDetail> userDetailResult = enterpriseWeixinUserService.getEnterpriseWeixinUserDetail(authResult.getData().getUser_ticket());
//        if (userDetailResult.failed()) {
//            return Result.valueOf(userDetailResult);
//        }
//
//        // 获取用户信息, 无需授权部分, 用户名
//        Result<EnterpriseUserInfo> userInfoResult = enterpriseWeixinUserService.getEnterpriseWeixinUserInfo(userId);
//        if (userInfoResult.failed()) {
//            return Result.valueOf(authResult);
//        }
//
//        EnterpriseUserDetail userDetail = userDetailResult.getData();
//
//        String email = StringUtil.isEmpty(userDetail.getEmail()) ? userDetail.getBiz_mail() : userDetail.getEmail();
//        UserPlatformPo platformPo = UserPlatformPo.builder()
//                .userId(detail.getUserId())
//                .userName(userInfoResult.getData().getName())
//                .avatar(userDetail.getAvatar())
//                .email(email)
//                .pwd(Initial_Password)
//                .needUpdatePwd(1)
//                .createTime(now)
//                .latestLoginTime(now)
//                .loginWay("weixin-oauth")
//                .tel(userDetail.getMobile())
//                .build();
//        int insert = userPlatformMapper.insertSelective(platformPo);
//        if (insert == 0) {
//            return Result.failure("用户初始化创建失败, 请重试一下");
//        }
//        sessionUser.setUserId(detail.getUserId());
//        sessionUser.setUserName(userInfoResult.getData().getName());
//        sessionUser.setTel(userDetail.getMobile());
//        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
//        sessionUser.setLoginTime(now);
//
//        String jwt = PlatformJwtUtil.createJwt(sessionUser);
//        return Result.success(new PlatformUserLoginDto(jwt));
        return R.failure("功能关闭");
    }

    @Override
    public R<SystemUserLoginDto> enterpriseWeixinQrCodeLogin(String authCode) {
//        Result<QrCodeAuthEnterpriseUserDto> authResult = enterpriseWeixinUserService.getEnterpriseWeixinUserId(authCode);
//        if (authResult.failed()) {
//            return Result.valueOf(authResult);
//        }
//
//        UserPlatformDetail detail = userPlatformMapper.detail(authResult.getData().getUserid());
//        if (detail == null) {
//            return Result.failure("该账号未注册,先进入企业微信, 打开工作台 -> 找到【Pyrange 后台管理系统】 -> 点击【后台系统】, 打开页面时点击【允许授权】");
//        }
//        UserPlatformPo platformPo = UserPlatformPo.builder()
//                .userId(detail.getUserId())
//                .latestLoginTime(LocalDateTime.now())
//                .loginWay("weixin-qr-code")
//                .build();
//        userPlatformMapper.updateById(platformPo);
//
//        SessionUser sessionUser = new SessionUser();
//        sessionUser.setUserId(detail.getUserId());
//        sessionUser.setUserName(detail.getUserName());
//        sessionUser.setTel(detail.getTel());
//        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
//        sessionUser.setLoginTime(LocalDateTime.now());
//        String jwt = PlatformJwtUtil.createJwt(sessionUser);
//        return Result.success(new PlatformUserLoginDto(jwt));
        return R.failure("功能关闭");
    }


}

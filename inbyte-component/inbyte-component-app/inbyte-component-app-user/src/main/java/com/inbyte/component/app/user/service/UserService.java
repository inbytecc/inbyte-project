package com.inbyte.component.app.user.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.model.*;


/**
 * 用户服务
 *
 * @author chenjw
 * @date: 2017/5/27
 */
public interface UserService {

    /**
     * 账号登录
     *
     * @param param
     * @return
     */
    R<UserLoginDto> emailLogin(UserEmailLoginParam param);

    /**
     * 用户登录验证
     *
     * @param appUser 用户登录信息
     * @return 用户登录信息
     */
    R<UserLoginDto> telLogin(UserTelLoginParam appUser);

    /**
     * 注册用户
     *
     * @param tel
     * @param nickname
     * @return 返回用户ID
     */
    R<Integer> register(String tel, String nickname, String avatar);

    UserBrief queryByTel(String tel);

    String getRandomCommonAvatar();

    R<UserLoginDto> register(UserRegisterParam param);

    R<UserBrief> info();

    R<UserLoginDto> emailForgetPwd(String email);

    R<UserLoginDto> emailResetPwd(EmailResetPwdParam emailResetPwdParam);

    R<UserLoginDto> registerWithEmail(UserRegisterWithVerifyCodeParam param);

    R<UserLoginDto> getRegisterEmailVerifyCode(String email);
}

package com.inbyte.component.app.user.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.model.*;

import java.math.BigDecimal;


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
     * @param nickName
     * @return 返回用户ID
     */
    R<Integer> register(String tel, String nickName, String avatar);

    UserBrief queryByTel(String tel);

    String getRandomCommonAvatar();

    void insertLocationSelective(Integer eid, Integer etp, Integer userId, BigDecimal longitude, BigDecimal latitude);

    R<UserLoginDto> register(UserRegisterParam param);

    R<UserBrief> info();

    R<UserLoginDto> emailForgetPwd(String email);

    R<UserLoginDto> emailResetPwd(EmailResetPwdParam emailResetPwdParam);
}

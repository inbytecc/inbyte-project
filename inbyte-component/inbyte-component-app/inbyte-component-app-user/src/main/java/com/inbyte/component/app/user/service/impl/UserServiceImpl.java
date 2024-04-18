package com.inbyte.component.app.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.api.CacheManager;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.IdentityGenerator;
import com.inbyte.commons.util.MD5Util;
import com.inbyte.commons.util.SpringContextUtil;
import com.inbyte.component.app.user.ComponentUserProperties;
import com.inbyte.component.app.user.api.UserRegisterEvent;
import com.inbyte.component.app.user.dao.UserMapper;
import com.inbyte.component.app.user.framework.SessionUser;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.model.*;
import com.inbyte.component.app.user.model.location.UserLocationPo;
import com.inbyte.component.app.user.service.UserService;
import com.inbyte.component.common.email.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * 用户服务
 * <p>
 * 易思网络
 *
 * @author chenjw
 * @date: 2017/5/27
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 默认头像
     */
    private String defaultAvatar = "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epA4X3XuP7q94mWyPMts1uR9D1HtnicwqbJIIewtqzMJuhbAGUzXCibweLibqucIxMRYC6v5h6M7icJnw/132";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ComponentUserProperties componentUserProperties;
    @Autowired(required = false)
    private MailService mailService;
    @Autowired(required = false)
    private CacheManager cacheManager;

    /**
     * 注册用户
     *
     * @param param 用户注册参数，包含必要的用户信息，如用户名、密码等。
     * @return 返回用户登录信息的封装对象，通常包含登录成功与否的状态和相关信息。
     */
    @Override
    public R<UserLoginDto> register(UserRegisterParam param) {
        LambdaQueryWrapper<UserPo> queryWrapper = new LambdaQueryWrapper<UserPo>()
                .eq(UserPo::getEmail, param.getEmail())
                .or().eq(UserPo::getTel, param.getTel())
                .or().eq(UserPo::getUserName, param.getUserName());
        UserPo userExists = userMapper.selectOne(queryWrapper);
        if (userExists != null) {
            if (userExists.getEmail() != null && userExists.getEmail().equals(param.getEmail())) {
                return R.failure("该邮箱已被注册");
            }
            if (userExists.getTel() != null && userExists.getTel().equals(param.getTel())) {
                return R.failure("该手机号已被注册");
            }
            if (userExists.getUserName() != null && userExists.getUserName().equals(param.getUserName())) {
                return R.failure("该用户名已被注册");
            }
        }

        UserPo userPo = UserPo.builder()
                .email(param.getEmail())
                .userName(param.getUserName())
                .nickname(param.getUserName())
                .tel(param.getTel())
                .pwd(MD5Util.md5(param.getPwd()))
                .createTime(LocalDateTime.now())
                .build();
        userMapper.insert(userPo);

        SessionUser sessionUser = SessionUser.builder()
                .userId(userPo.getUserId())
                .tel(userPo.getTel())
                .nickname(param.getUserName())
                .avatar(userPo.getAvatar())
                .loginTime(LocalDateTime.now())
                .tokenVersion(SessionUtil.User_Token_Version)
                .build();
        return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.Yes.code));
    }

    /**
     * 邮箱登录
     * 使用邮箱登录参数进行用户登录验证。
     *
     * @param param 包含用户邮箱和密码等登录必要信息的参数对象。
     * @return 返回一个包含登录结果的响应对象。成功时，包含登录成功的用户信息；失败时，包含错误信息。
     */
    @Override
    public R<UserLoginDto> emailLogin(UserEmailLoginParam param) {
        LambdaQueryWrapper<UserPo> queryWrapper = new LambdaQueryWrapper<UserPo>()
                .eq(UserPo::getEmail, param.getEmail())
                .eq(UserPo::getPwd, MD5Util.md5(param.getPwd()))
                .select(UserPo::getUserId, UserPo::getTel, UserPo::getNickname, UserPo::getAvatar);
        UserPo userPo = userMapper.selectOne(queryWrapper);
        if (userPo == null) {
            return R.failure("账号或密码错误");
        }

        SessionUser sessionUser = SessionUser.builder()
                .userId(userPo.getUserId())
                .tel(userPo.getTel())
                .nickname(userPo.getNickname())
                .avatar(userPo.getAvatar())
                .loginTime(LocalDateTime.now())
                .tokenVersion(SessionUtil.User_Token_Version)
                .build();
        return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.Yes.code));
    }


    /**
     * 手机号登录
     * 暂供测试调试使用，非正常接口
     *
     * @param param
     * @return
     */
    @Override
    public R<UserLoginDto> telLogin(UserTelLoginParam param) {
        if (!"88888888".equals(param.getPwd())) {
            return R.failure("账号或密码错误");
        }
        LambdaQueryWrapper<UserPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPo::getTel, param.getTel())
                .select(UserPo::getUserId, UserPo::getNickname, UserPo::getAvatar, UserPo::getTel);
        UserPo userPo = userMapper.selectOne(queryWrapper);
        if (userPo == null) {
            return R.failure("用户不存在哦, 请先注册后登录");
        }

        SessionUser sessionUser = SessionUser.builder()
                .userId(userPo.getUserId())
                .tel(userPo.getTel())
                .eid(1)
                .etp(AppTypeEnum.WXMP)
                .nickname(userPo.getNickname())
                .avatar(userPo.getAvatar())
                .loginTime(LocalDateTime.now())
                .tokenVersion(SessionUtil.User_Token_Version)
                .telBound(WhetherDict.Yes.code)
                .build();
        return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.Yes.code));
    }

    /**
     * 注册用户
     *
     * @param tel
     * @param nickname
     * @return 返回用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<Integer> register(String tel, String nickname, String avatar) {
        // 新注册账号
        UserPo userPo = UserPo.builder()
                .tel(tel)
                .userName(nickname)
                .nickname(nickname)
                .avatar(avatar)
                .createTime(LocalDateTime.now())
                .build();
        int insert = userMapper.insertSelective(userPo);
        if (insert == 0) {
            return R.failure("用户注册失败, 请重试一下");
        }

        registerEventInvoke(userPo.getUserId());

        return R.ok("注册成功", userPo.getUserId());
    }

    /**
     * 用户登录事件回调
     *
     * @param userId
     */
    private void registerEventInvoke(Integer userId) {
        Map<String, UserRegisterEvent> userRegisterEventMap = SpringContextUtil.getContext().getBeansOfType(UserRegisterEvent.class);
        Iterator<UserRegisterEvent> iterator = userRegisterEventMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().register(userId);
        }
    }

    @Override
    public UserBrief queryByTel(String tel) {
        return userMapper.queryByTel(tel);
    }

    @Override
    public String getRandomCommonAvatar() {
        if (componentUserProperties.getAvatars() != null && !componentUserProperties.getAvatars().isEmpty()) {
            return componentUserProperties.getAvatars().get(new Random().nextInt(componentUserProperties.getAvatars().size()));
        }
        return defaultAvatar;
    }

    @Override
    public void insertLocationSelective(Integer eid, AppTypeEnum etp, Integer userId, BigDecimal longitude, BigDecimal latitude) {
        UserLocationPo locationPo = UserLocationPo.builder()
                .etp(etp)
                .eid(eid)
                .userId(userId)
                .longitude(longitude)
                .latitude(latitude)
                .createTime(LocalDateTime.now())
                .build();
        userMapper.insertLocationSelective(locationPo);
    }

    @Override
    public R<UserBrief> info() {
        UserPo userPo = userMapper.selectById(SessionUtil.getUserId());
        UserBrief userBrief = new UserBrief();
        BeanUtils.copyProperties(userPo, userBrief);
        return R.ok(userBrief);
    }

    @Override
    public R<UserLoginDto> emailForgetPwd(String email) {
        // 发送邮件
        sendEmailVerifyCode(email,
                componentUserProperties.getEmail().getForgetPwdTitle(),
                componentUserProperties.getEmail().getForgetPwdContent());
        return R.ok("验证码发送成功");
    }

    @Override
    public R<UserLoginDto> emailResetPwd(EmailResetPwdParam emailResetPwdParam) {
        if (!verifyCode(emailResetPwdParam.getEmail(), emailResetPwdParam.getVerifyCode())) {
            return R.failure("验证码不正确");
        }

        LambdaUpdateWrapper<UserPo> updateWrapper = new LambdaUpdateWrapper<UserPo>()
                .set(UserPo::getPwd, MD5Util.md5(emailResetPwdParam.getPwd()))
                .set(UserPo::getUpdateTime, LocalDateTime.now())
                .eq(UserPo::getEmail, emailResetPwdParam.getEmail());
        userMapper.update(null, updateWrapper);

        UserEmailLoginParam userEmailLoginParam = new UserEmailLoginParam();
        userEmailLoginParam.setEmail(emailResetPwdParam.getEmail());
        userEmailLoginParam.setPwd(emailResetPwdParam.getPwd());
        return emailLogin(userEmailLoginParam);
    }

    @Override
    public R<UserLoginDto> registerWithEmail(UserRegisterWithVerifyCodeParam param) {
        if (!verifyCode(param.getEmail(), param.getVerifyCode())) {
            return R.failure("验证码不正确");
        }
        return register(param);
    }

    @Override
    public R<UserLoginDto> getRegisterEmailVerifyCode(String email) {
        // 发送邮件
        sendEmailVerifyCode(email,
                componentUserProperties.getEmail().getRegisterTitle(),
                componentUserProperties.getEmail().getRegisterContent());
        return R.ok("验证码发送成功");
    }

    private void sendEmailVerifyCode( String email, String title, String content) {
        // 随机生成验证码
        String verifyCode = IdentityGenerator.generateRandomDigitalCode();
        cacheManager.put(email + "-" + verifyCode);

        // 发送邮件
        mailService.sendSimpleMail(email, title, String.format(content, verifyCode));
    }

    private boolean verifyCode(String email, String verifyCode) {
        return cacheManager.containsKey(email + "-" + verifyCode);
    }
}

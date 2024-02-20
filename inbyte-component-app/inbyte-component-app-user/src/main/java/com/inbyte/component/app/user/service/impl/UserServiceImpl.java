package com.inbyte.component.app.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.component.app.user.ComponentUserProperties;
import com.inbyte.component.app.user.model.location.UserLocationPo;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.SpringContextUtil;
import com.inbyte.component.app.user.api.UserRegisterEvent;
import com.inbyte.component.app.user.dao.UserMapper;
import com.inbyte.component.app.user.framework.SessionUser;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.model.UserBrief;
import com.inbyte.component.app.user.model.UserLoginDto;
import com.inbyte.component.app.user.model.UserPo;
import com.inbyte.component.app.user.model.UserTelLoginParam;
import com.inbyte.component.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
 * 杭州湃橙体育科技有限公司
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
                .select(UserPo::getUserId, UserPo::getNickName, UserPo::getAvatarUrl);
        UserPo userPo = userMapper.selectOne(queryWrapper);
        if (userPo == null) {
            return R.failure("用户不存在哦, 请先注册后登录");
        }

        SessionUser sessionUser = SessionUser.builder()
                .userId(userPo.getUserId())
                .tel(userPo.getTel())
                .eid(1)
                .etp(0)
                .nickName(userPo.getNickName())
                .avatar(userPo.getAvatarUrl())
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
     * @param nickName
     * @return 返回用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<Integer> register(String tel, String nickName, String avatar) {
        // 新注册账号
        UserPo userPo = UserPo.builder()
                .tel(tel)
                .userName(nickName)
                .nickName(nickName)
                .avatarUrl(avatar)
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
    public void insertLocationSelective(Integer eid, Integer etp, Integer userId, BigDecimal longitude, BigDecimal latitude) {
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
}

package com.inbyte.component.app.user.weixin.mp.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.BasePage;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.commons.model.enums.AppTypeEnum;
import com.inbyte.commons.util.SpringContextUtil;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.app.sign.framework.AppUtil;
import com.inbyte.commons.model.enums.RecommendTypeEnum;
import com.inbyte.component.app.user.event.UserFirstTimeLoginEvent;
import com.inbyte.component.app.user.event.UserLocationUpdateEvent;
import com.inbyte.component.app.user.event.UserRegisterEvent;
import com.inbyte.component.app.user.framework.SessionUser;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.model.LocationUpdate;
import com.inbyte.component.app.user.model.UserBrief;
import com.inbyte.component.app.user.service.UserService;
import com.inbyte.component.app.user.weixin.mp.dao.UserWeixinMpInviteMapper;
import com.inbyte.component.app.user.weixin.mp.dao.UserWeixinMpMapper;
import com.inbyte.component.app.user.weixin.mp.model.*;
import com.inbyte.util.weixin.mp.client.WxMpUserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 微信小程序用户服务
 *
 * @author chenjw
 * @date 2023-09-15 16:55:46
 **/
@Slf4j
@Service
public class UserWeixinMpServiceImpl implements UserWeixinMpService {

    @Autowired
    private UserWeixinMpMapper userWeixinMpMapper;
    @Autowired
    private UserWeixinMpInviteMapper inviteMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private WxMpUserClient wxMpUserClient;

    /**
     * 微信小程序登录
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<UserLoginDto> login(WeixinLoginParam param) {
        SessionUser sessionUser;
        LocalDateTime now = LocalDateTime.now();

        // 获取微信小程序登录用户证明
        R<WxMaJscode2SessionResult> weixinUserCredentialR = wxMpUserClient.code2session(AppUtil.getAppId(), param.getOpenIdJsCode());
        log.info("微信用户登录 code2session 结果:{}", JSON.toJSONString(weixinUserCredentialR));
        if (weixinUserCredentialR.failed()) {
            return R.valueOf(weixinUserCredentialR);
        }
        WxMaJscode2SessionResult credentialDto = weixinUserCredentialR.getData();

        // 查询微信小程序用户信息
        UserWeixinDetail userWeixinDetail = userWeixinMpMapper.detail(credentialDto.getOpenid());

        // 如果微信用户未创建, 新增基本信息, 并且提示注册
        if (userWeixinDetail == null) {
            String randomCommonAvatar = userService.getRandomCommonAvatar();

            // 用户推荐源逻辑处理
            RecommendTypeEnum recommendType = RecommendTypeEnum.OT;
            if (param.getS() != null) {
                recommendType = RecommendTypeEnum.USER;
            } else if (param.getT() != null) {
                // 20240815 记录，1.2版本去除此逻辑处理，修改为其它字段判断
                if (param.getT() == 0) {
                    recommendType = RecommendTypeEnum.USER;
                } else {
                    recommendType = RecommendTypeEnum.MERCHANT;
                }
            }

            UserWeixinMpPo weixinPo = UserWeixinMpPo.builder()
                    .openId(credentialDto.getOpenid())
                    .unionId(credentialDto.getUnionid())
                    .nickname("游客")
                    .avatar(randomCommonAvatar)
                    .sessionKey(credentialDto.getSessionKey())
                    .appId(AppUtil.getAppId())
                    .mctNo(AppUtil.getMctNo())
                    .createTime(now)
                    .referrerEid(param.getS())
                    .qcid(param.getQ())
                    .recommendType(recommendType)
                    .latestLoginTime(now)
                    .registerLongitude(param.getLongitude())
                    .registerLatitude(param.getLatitude())
                    .longitude(param.getLongitude())
                    .latitude(param.getLatitude())
                    .build();
            log.info("新用户注册:{}", JSON.toJSONString(weixinPo));
            userWeixinMpMapper.insert(weixinPo);

            // 因为是首次注册，记录推荐绑定关系信息
            if (param.getT() != null) {
                // 处理推荐分享、二维码分享关系绑定处理
                UserFirstTimeLoginEvent firstTimeLoginEvent = new UserFirstTimeLoginEvent(this,
                        param.getQ(), param.getT(), weixinPo.getEid(), AppTypeEnum.WXMP, param.getS(), now,
                        param.getLongitude(), param.getLatitude(), AppUtil.getAppId(), AppUtil.getMctNo());
                log.info("微信新用户首次登录, 且存在推荐关系, 发布事件:{}", JSON.toJSONString(firstTimeLoginEvent));
                SpringContextUtil.getContext().publishEvent(firstTimeLoginEvent);
            }

            sessionUser = SessionUser.builder()
                    .eid(weixinPo.getEid())
                    .openId(weixinPo.getOpenId())
                    .appType(AppTypeEnum.WXMP)
                    .nickname("游客")
                    .avatar(randomCommonAvatar)
                    .loginTime(now)
                    .tokenVersion(SessionUtil.User_Token_Version)
                    .telBound(WhetherDict.No.code)
                    .referrerEid(param.getS())
                    .build();
            return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.No.code));
        }

        // 非首次注册登录用户
        // 更新微信用户表
        LambdaUpdateWrapper<UserWeixinMpPo> updateWrapper = new LambdaUpdateWrapper<UserWeixinMpPo>()
                .eq(UserWeixinMpPo::getEid, userWeixinDetail.getEid())
                .set(UserWeixinMpPo::getLongitude, param.getLongitude())
                .set(UserWeixinMpPo::getLatitude, param.getLatitude())
                .set(UserWeixinMpPo::getLatestLoginTime, now)
                .setSql("login_count = login_count + 1");
        // 新登录微信 unionId 不为空, 而之前 unionId 为空
        // 说明首次注册小程序之后又关注了公众号, 再更新 unionId 信息, 以便账户关联, 发送用户消息等
        if (StringUtil.isEmpty(userWeixinDetail.getUnionId()) && StringUtil.isNotEmpty(credentialDto.getUnionid())) {
            updateWrapper.set(UserWeixinMpPo::getUnionId, userWeixinDetail.getUnionId());
        }
        userWeixinMpMapper.update(null, updateWrapper);

        // 如果未绑定手机号用户处理
        if (userWeixinDetail.getBoundWithUser() == WhetherDict.No.code) {
            sessionUser = SessionUser.builder()
                    .eid(userWeixinDetail.getEid())
                    .openId(userWeixinDetail.getOpenId())
                    .appType(AppTypeEnum.WXMP)
                    .nickname("游客")
                    .avatar(userWeixinDetail.getAvatar())
                    .loginTime(now)
                    .tokenVersion(SessionUtil.User_Token_Version)
                    .telBound(WhetherDict.No.code)
                    .referrerEid(param.getS())
                    .build();
            return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.No.code));
        }

        // 已绑定手机号用户 Session 信息
        sessionUser = SessionUser.builder()
                .eid(userWeixinDetail.getEid())
                .openId(userWeixinDetail.getOpenId())
                .appType(AppTypeEnum.WXMP)
                .userId(userWeixinDetail.getUserId())
                .tel(userWeixinDetail.getTel())
                .nickname(userWeixinDetail.getNickname())
                .avatar(userWeixinDetail.getAvatar())
                .loginTime(now)
                .tokenVersion(SessionUtil.User_Token_Version)
                .telBound(WhetherDict.Yes.code)
                .referrerEid(userWeixinDetail.getReferrerEid())
                .build();
        return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.Yes.code));
    }

    /**
     * 微信获取手机号注册用户
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<UserLoginDto> register(UserWeixinRegisterParam param) {
        if (!SessionUtil.silentLogged()) {
            log.error("未登录用户, 调用注册接口, 程序异常");
            return R.error("未登录用户, 调用注册接口, 程序异常");
        }
        R<WxMaPhoneNumberInfo> weixinUserTel = wxMpUserClient.getPhoneInfo(AppUtil.getAppId(), param.getPhoneNumberJsCode());
        if (weixinUserTel.failed()) {
            return R.valueOf(weixinUserTel);
        }
        WxMaPhoneNumberInfo phoneInfo = weixinUserTel.getData();
        UserBrief userBrief = userService.queryByTel(phoneInfo.getPurePhoneNumber());

        // 在整个平台都没有注册过的用户
        R<UserLoginDto> userLoginDtoR;
        if (userBrief == null) {
            userLoginDtoR = registerNewUser(param, phoneInfo);
        } else {
            // 在其它我们开发的小程序注册过的用户
            userLoginDtoR = registerHalfNewUser(param, phoneInfo, userBrief);
        }

        return userLoginDtoR;
    }

    /**
     * 没有在整个平台注册过的用户
     *
     * @param param
     * @param phoneInfo
     * @return
     */
    private R<UserLoginDto> registerNewUser(UserWeixinRegisterParam param, WxMaPhoneNumberInfo phoneInfo) {
        LocalDateTime now = LocalDateTime.now();
        SessionUser sessionUser = SessionUtil.getSessionUser();
        String nickname = "用户" + phoneInfo.getPurePhoneNumber().substring(7);

        // 新注册账号
        R<Integer> register = userService.register(phoneInfo.getPurePhoneNumber(), nickname, sessionUser.getAvatar());
        if (register.failed()) {
            return R.failure("用户注册失败, 请重试一下");
        }

        // 更新用户微信表
        UserWeixinMpPo userWeixinMpPo = UserWeixinMpPo.builder()
                .eid(sessionUser.getEid())
                .userId(register.getData())
                .nickname("微信" + nickname)
                .tel(phoneInfo.getPurePhoneNumber())
                .countryCode(phoneInfo.getCountryCode())
                .boundWithUser(WhetherDict.Yes.code)
                .boundWithUserTime(now)
                .registerLongitude(param.getLongitude())
                .registerLatitude(param.getLatitude())
                .build();

        int update = userWeixinMpMapper.updateById(userWeixinMpPo);
        if (update == 0) {
            throw InbyteException.failure("注册账号异常, 请重试看看");
        }

        // 发布用户注册事件
        UserRegisterEvent userRegisterEvent = new UserRegisterEvent(this,
                register.getData(), sessionUser.getEid(), sessionUser.getAppType(), now,
                AppUtil.getAppId(), AppUtil.getMctNo());
        SpringContextUtil.getContext().publishEvent(userRegisterEvent);

        // 用户 Session 信息
        sessionUser = SessionUser.builder()
                .eid(sessionUser.getEid())
                .openId(sessionUser.getOpenId())
                .appType(AppTypeEnum.WXMP)
                .userId(register.getData())
                .tel(phoneInfo.getPurePhoneNumber())
                .nickname("微信" + nickname)
                .avatar(sessionUser.getAvatar())
                .referrerEid(sessionUser.getReferrerEid())
                .loginTime(now)
                .tokenVersion(SessionUtil.User_Token_Version)
                .telBound(WhetherDict.Yes.code)
                .build();
        return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.Yes.code));
    }

    /**
     * 在其它我们开发的小程序注册过的用户
     *
     * @param param
     * @param phoneInfo
     * @return
     */
    private R<UserLoginDto> registerHalfNewUser(UserWeixinRegisterParam param, WxMaPhoneNumberInfo phoneInfo, UserBrief userBrief) {
        LocalDateTime now = LocalDateTime.now();
        SessionUser sessionUser = SessionUtil.getSessionUser();
        String nickname = "微信用户" + phoneInfo.getPurePhoneNumber().substring(7);

        // 更新用户微信表
        UserWeixinMpPo userWeixinPO = UserWeixinMpPo.builder()
                .eid(sessionUser.getEid())
                .userId(userBrief.getUserId())
                .nickname(nickname)
                .tel(phoneInfo.getPurePhoneNumber())
                .countryCode(phoneInfo.getCountryCode())
                .boundWithUser(WhetherDict.Yes.code)
                .boundWithUserTime(now)
                .registerLongitude(param.getLongitude())
                .registerLatitude(param.getLatitude())
                .build();
        int update = userWeixinMpMapper.updateById(userWeixinPO);
        if (update == 0) {
            throw InbyteException.failure("注册账号异常, 请重试看看");
        }

        // 用户 Session 信息
        sessionUser = SessionUser.builder()
                .eid(sessionUser.getEid())
                .openId(sessionUser.getOpenId())
                .appType(AppTypeEnum.WXMP)
                .userId(userBrief.getUserId())
                .tel(phoneInfo.getPurePhoneNumber())
                .nickname(nickname)
                .avatar(sessionUser.getAvatar())
                .referrerEid(sessionUser.getReferrerEid())
                .loginTime(now)
                .tokenVersion(SessionUtil.User_Token_Version)
                .telBound(WhetherDict.Yes.code)
                .build();

        // 发布用户注册事件
        UserRegisterEvent userRegisterEvent = new UserRegisterEvent(this,
                userBrief.getUserId(), sessionUser.getEid(), sessionUser.getAppType(), now,
                AppUtil.getAppId(), AppUtil.getMctNo());
        SpringContextUtil.getContext().publishEvent(userRegisterEvent);

        return R.ok(new UserLoginDto(SessionUtil.getJwtToken(sessionUser), WhetherDict.Yes.code));
    }

    /**
     * 更新当前微信用户定位
     * <p>
     * 非必须注册
     *
     * @param locationUpdate
     * @return
     */
    @Override
    public R updateLocation(LocationUpdate locationUpdate) {
        SessionUser sessionUser = SessionUtil.getSessionUserUnchecked();
        if (sessionUser == null) {
            return R.ok();
        }

        // 更新用户当前定位
        LocalDateTime now = LocalDateTime.now();
        UserWeixinMpPo userWeixinMpPo = UserWeixinMpPo.builder()
                .eid(sessionUser.getEid())
                .longitude(locationUpdate.getLongitude())
                .latitude(locationUpdate.getLatitude())
                .locationUpdateTime(now)
                .build();
        userWeixinMpMapper.updateById(userWeixinMpPo);

        // 发布定位更新事件
        UserLocationUpdateEvent locationUpdateEvent = new UserLocationUpdateEvent(this,
                sessionUser.getUserId(), sessionUser.getEid(), AppUtil.getMctNo(), sessionUser.getAppType(),
                locationUpdate.getLongitude(), locationUpdate.getLatitude(), now);
        SpringContextUtil.getContext().publishEvent(locationUpdateEvent);

        return R.ok();
    }

    /**
     * 个人信息
     *
     * @return
     */
    @Override
    public R<UserWeixinDto> info() {
        UserWeixinDto info = userWeixinMpMapper.info(SessionUtil.getEid());
        if (info == null) {
            return R.set(ResultStatus.Unauthorized, "用户信息查询失败");
        }
        return R.ok(info);
    }

    @Override
    public R<String> update(UserWeixinMpUpdate userWeixinMpUpdate) {
        LambdaUpdateWrapper<UserWeixinMpPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserWeixinMpPo::getEid, SessionUtil.getEid());
        updateWrapper.set(UserWeixinMpPo::getNickname, userWeixinMpUpdate.getNickname());
        updateWrapper.set(UserWeixinMpPo::getAvatar, userWeixinMpUpdate.getAvatar());
        userWeixinMpMapper.update(null, updateWrapper);
        return R.ok("修改成功");
    }


    /**
     * 总邀请人数
     *
     * @return
     */
    @Override
    public R<Integer> count() {
        if (!AppUtil.isWeixinMp()) {
            return R.failure("暂时不支持查询邀请数据");
        }
        LambdaQueryWrapper<UserWeixinMpPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserWeixinMpPo::getInviteCount);
        queryWrapper.eq(UserWeixinMpPo::getEid, SessionUtil.getEid());
        UserWeixinMpPo userWeixinMpPo = userWeixinMpMapper.selectOne(queryWrapper);
        return R.ok("ok", userWeixinMpPo.getInviteCount());
    }

    /**
     * 邀请列表
     *
     * @param basePage
     * @return
     */
    @Override
    public R<List<UserInviteDto>> list(BasePage basePage) {
        if (!AppUtil.isWeixinMp()) {
            return R.failure("暂时不支持查询邀请数据");
        }
        PageHelper.startPage(basePage);
        return R.ok(inviteMapper.list(SessionUtil.getEid()));
    }
}

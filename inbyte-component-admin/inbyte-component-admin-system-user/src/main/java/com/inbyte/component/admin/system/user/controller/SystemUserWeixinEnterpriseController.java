package com.inbyte.component.admin.system.user.controller;

import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;
import com.inbyte.component.admin.system.user.service.SystemUsertWeixinEnterpriseService;
import com.inbyte.commons.model.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 商户用户企业微信
 *
 * @author chenjw
 * @date 2022-11-01 10:22:12
 **/
@RestController
@RequestMapping( "user/merchant/weixin/enterprise")
public class SystemUserWeixinEnterpriseController {

    @Autowired
    private SystemUsertWeixinEnterpriseService systemUsertWeixinEnterpriseService;


    /**
     * 企微授权登录
     * <p>
     * 企业微信 OAuth 网页授权登录</br>
     * 参考文档：https://developer.work.weixin.qq.com/document/path/91335
     * <p>
     * 企业微信授权登录参数: </br>
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww4b02eb8d9a97ad6c&redirect_uri=https://m.inbyte.cc/api/user-platform/login/enterprise-weixin-oauth&response_type=code&scope=snsapi_privateinfo&state=STATE&agentid=1000002#wechat_redirect </br>
     * 注: 需要在企业微信内访问才有效
     *
     * @param authCode
     * @return
     */
    @GetMapping("login/enterprise-weixin-oauth")
    public R<SystemUserLoginDto> enterpriseWeixinOAuthLogin(@RequestParam("code") String authCode) {
        return systemUsertWeixinEnterpriseService.enterpriseWeixinOAuthLogin(authCode);
    }

    /**
     * 企微扫码登录
     * <p>
     * 企业微信扫码授权登录</br>
     * 参考文档：https://developer.work.weixin.qq.com/document/path/91025
     * <p>
     * 企业微信授权登录参数参考: </br>
     * https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=ww4b02eb8d9a97ad6c&agentid=1000002&redirect_uri=https://m.inbyte.cc/api/user-platform/login/enterprise-weixin-qr-code&state=STATE</br>
     *
     * @param authCode
     * @return
     */
    @GetMapping("login/enterprise-weixin-qr-code")
    public R<SystemUserLoginDto> enterpriseWeixinQrCodeLogin(@RequestParam("code") String authCode) {
        return systemUsertWeixinEnterpriseService.enterpriseWeixinQrCodeLogin(authCode);
    }

//    /**
//     * 企微扫码配置
//     * <p>
//     *
//     * @return
//     */
//    @GetMapping("config/enterprise-weixin-qr-code")
//    public Result<EnterpriseQrCodeConfigAppDto> enterpriseWeixinQrCodeConfig() {
//        return userPlatformService.enterpriseWeixinQrCodeConfig();
//    }

}

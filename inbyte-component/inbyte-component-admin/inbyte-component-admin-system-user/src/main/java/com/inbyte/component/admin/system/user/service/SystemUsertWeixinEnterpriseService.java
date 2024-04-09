package com.inbyte.component.admin.system.user.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;

/**
 * 商户用户企业微信服务
 *
 * @author : chenjw
 * @date: 2022/11/01
 **/
public interface SystemUsertWeixinEnterpriseService {

    /**
     * 企微授权登录
     * @param authCode
     * @return
     */
    R<SystemUserLoginDto> enterpriseWeixinOAuthLogin(String authCode);

    /**
     * 企微扫描登录
     * @param authCode
     * @return
     */
    R<SystemUserLoginDto> enterpriseWeixinQrCodeLogin(String authCode);


//    /**
//     * 扫码登录配置获取
//     * @return
//     */
//    Result<EnterpriseQrCodeConfigAppDto> enterpriseWeixinQrCodeConfig();

}

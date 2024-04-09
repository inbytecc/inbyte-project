package com.inbyte.component.app.sign.service.impl;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.sign.framework.AppInfo;
import com.inbyte.component.app.sign.framework.AppJwtUtil;
import com.inbyte.component.app.sign.service.AppSignService;
import com.inbyte.component.app.sign.model.AppSignDto;
import com.inbyte.component.app.sign.model.AppSignParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * App 服务
 * <p>
 * 易思网络
 *
 * @author chenjw
 * @date: 2022/12/31
 */
@Slf4j
@Service
public class AppSignServiceImpl implements AppSignService {

//    @Autowired
//    private MerchantMapper merchantMapper;

    @Override
    public R<AppSignDto> appSign(AppSignParam param) {
        AppInfo appInfo = new AppInfo();
//        if (param.getAppType() == null || param.getAppType() == AppTypeDict.Weixin_MiniProgram.code) {
//            WeixinMiniProgramConfigBrief brief = weixinConfigMapper.miniProgramBrief(param.getAppId());
//            if (brief == null) {
//                return Result.failure("该 AppID 未在系统注册, 请联系商务人员");
//            }
//
//            appInfo.setAppId(brief.getAppId());
//            appInfo.setMctNo(brief.getMctNo());
//        } else if (param.getAppType() == AppTypeDict.Alipay_MiniProgram.code) {
//            appInfo.setAppId(param.getAppId());
//            appInfo.setMctNo("1");
//        } else {
//            return Result.failure("该 AppID 未在系统注册, 请联系商务人员");
//        }
        appInfo.setAppType(param.getAppType());
        appInfo.setAppId(param.getAppId());
        appInfo.setMctNo("1");
        appInfo.setAppVersion(param.getAppVersion());
        appInfo.setSignTime(LocalDateTime.now());
        String appToken = AppJwtUtil.createJwt(appInfo);
        return R.ok(new AppSignDto(appToken));
    }


}

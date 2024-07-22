package com.inbyte.component.app.sign.service.impl;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.sign.dao.InbyteAppMapper;
import com.inbyte.component.app.sign.model.AppInfo;
import com.inbyte.component.app.sign.framework.AppJwtUtil;
import com.inbyte.component.app.sign.model.AppSignDto;
import com.inbyte.component.app.sign.model.AppSignParam;
import com.inbyte.component.app.sign.model.InbyteAppPo;
import com.inbyte.component.app.sign.service.AppSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private InbyteAppMapper inbyteAppMapper;

    @Override
    public R<AppSignDto> appSign(AppSignParam param) {
        InbyteAppPo inbyteAppPo = inbyteAppMapper.selectById(param.getAppId());
        if (inbyteAppPo == null) {
            return R.failure("该 AppID 未在系统注册, 请联系商务人员");
        }

        AppInfo appInfo = new AppInfo();
        appInfo.setAppType(param.getAppType());
        appInfo.setAppId(param.getAppId());
        appInfo.setMctNo(inbyteAppPo.getMctNo());
        appInfo.setAppVersion(param.getAppVersion());
        appInfo.setSignTime(LocalDateTime.now());
        String appToken = AppJwtUtil.createJwt(appInfo);
        return R.ok(new AppSignDto(appToken));
    }


}

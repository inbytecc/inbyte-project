package com.inbyte.component.app.sign.framework;

import com.inbyte.commons.model.enums.AppTypeEnum;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.commons.util.WebUtil;
import com.inbyte.component.app.sign.framework.exception.AppTokenUnavailableException;
import com.inbyte.component.app.sign.model.AppInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * App 工具
 *
 * @author: chenjw
 * @date: 2020/11/24
 */
@Slf4j
public class AppUtil {

    /**
     * 应用凭证头
     */
    private static final String App_Token = "App-Token";

    /**
     * 获取 App 应用对象
     *
     * @return
     */
    public static AppInfo getAppInfo() {
        String authorization = WebUtil.getHeader(App_Token);
        if (StringUtil.isEmpty(authorization)) {
            throw new AppTokenUnavailableException();
        }
        return AppJwtUtil.parseObject(authorization);
    }
    /**
     * 获取 App 应用对象
     *
     * @return
     */
    public static AppInfo getAppInfoUncheck() {
        String authorization = WebUtil.getHeader(App_Token);
        if (StringUtil.isEmpty(authorization)) {
            return null;
        }
        return AppJwtUtil.parseObject(authorization);
    }

    /**
     * 获取小程序AppId
     *
     * @return
     */
    public static String getAppId() {
        AppInfo appInfo = getAppInfo();
        if (appInfo == null) {
            log.error("前端未上传AppId字段, 提醒尽快处理");
//            return "wx07d7401ebddff980";
            throw new AppTokenUnavailableException();
        }
        return appInfo.getAppId();
    }

    /**
     * 获取所属商户ID
     *
     * @return
     */
    public static String getMctNo() {
        return getAppInfo().getMctNo();
    }

    public static AppTypeEnum getAppType() {
        AppTypeEnum appType = getAppInfo().getAppType();
        if (appType == null) {
            appType = AppTypeEnum.WXMP;
        }
        return appType;
    }

    public static boolean isWeixinMp() {
        return getAppInfo().getAppType() == AppTypeEnum.WXMP;
    }

    public static boolean isAlipayMp() {
        return getAppInfo().getAppType() == AppTypeEnum.ALIMP;
    }
}

package com.inbyte.component.app.user.weixin.mp;

import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.app.user.dict.UserSourceTypeDict;

/**
 * 小程序Scene处理
 * @author chenjw
 */
public class SceneUtil {



    /**
     * 生成小程序访问scene
     * 用户eid + 路径参数
     * @param eid
     * @param
     * @return
     */
    public static String getUserShareScene(Integer eid, JSONObject pathParam) {
        StringBuilder scene = new StringBuilder()
                .append("s=").append(eid)
                .append("&t=").append(UserSourceTypeDict.User_Share.code);

        String pathParamStr = StringUtil.jsonToStr(pathParam);
        if (StringUtil.isNotEmpty(pathParamStr)) {
            scene.append("&").append(pathParamStr);
        }
        return scene.toString();
    }

}

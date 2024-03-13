package com.inbyte.component.app.basic;

import com.inbyte.commons.util.SpringContextUtil;
import com.inbyte.component.app.basic.dao.SystemConfigMapper;

/**
 * 系统配置工具类
 *
 * @author chenjw
 * @date 20240312
 */
public class SystemConfigClientUtil {

    /**
     * 获取系统配置值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return SpringContextUtil.getBean(SystemConfigMapper.class).getValue(key);
    }
}

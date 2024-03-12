package com.inbyte.component.app.basic.service.impl;

import com.inbyte.component.app.basic.dao.SystemConfigMapper;
import com.inbyte.component.app.basic.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统配置服务
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public String getValue(String key) {
        return systemConfigMapper.getValue(key);
    }
}

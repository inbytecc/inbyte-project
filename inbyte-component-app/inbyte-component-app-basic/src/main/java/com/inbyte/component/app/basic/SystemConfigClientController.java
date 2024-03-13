package com.inbyte.component.app.basic;

import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.basic.service.SystemConfigClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@RestController
@RequestMapping("system/config")
public class SystemConfigClientController {

    @Autowired
    private SystemConfigClientService systemConfigClientService;


    /**
     * Key获取 String Value
     *
     * @param key
     * @return R<String>
     **/
    @GetMapping("{key}")
    public R<String> value(@PathVariable("key") String key) {
        return R.okStr(systemConfigClientService.getValue(key));
    }

    /**
     * Key获取Value
     *
     * @param key
     * @return R<String>
     **/
    @GetMapping("{key}/json")
    public R<Object> json(@PathVariable("key") String key) {
        return R.ok(JSON.parse(systemConfigClientService.getValue(key)));
    }

}

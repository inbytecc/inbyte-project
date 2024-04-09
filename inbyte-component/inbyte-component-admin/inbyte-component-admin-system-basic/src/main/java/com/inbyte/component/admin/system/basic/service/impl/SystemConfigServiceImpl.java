package com.inbyte.component.admin.system.basic.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.basic.model.*;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.basic.service.SystemConfigService;
import com.inbyte.component.admin.system.basic.dao.SystemConfigMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

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
    public R insert(SystemConfigInsert insert) {
        LambdaQueryWrapper<SystemConfigPo> query = new LambdaQueryWrapper<SystemConfigPo>()
                .eq(SystemConfigPo::getKey, insert.getKey());
        Long l = systemConfigMapper.selectCount(query);
        if (l > 0) {
            return R.error("配置项key已存在");
        }

        SystemConfigPo systemConfigPo = SystemConfigPo.builder()
                .createTime(LocalDateTime.now())
                .creatorId(SessionUtil.getUserId())
                .creatorName(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, systemConfigPo);
        systemConfigMapper.insert(systemConfigPo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer id) {
        systemConfigMapper.deleteById(id);
        return R.ok("删除成功");
    }

    @Override
    public R update(SystemConfigUpdate update) {
        SystemConfigPo systemConfigPo = SystemConfigPo.builder()
                .updateTime(LocalDateTime.now())
                .modifierId(SessionUtil.getUserId())
                .modifierName(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, systemConfigPo);

        systemConfigMapper.updateById(systemConfigPo);
        return R.ok("修改成功");
    }

    @Override
    public R updateByKey(SystemConfigUpdateByKey update) {
        SystemConfigPo systemConfigPo = SystemConfigPo.builder()
                .updateTime(LocalDateTime.now())
                .modifierId(SessionUtil.getUserId())
                .modifierName(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, systemConfigPo);

        String val;
        if (update.getValue() instanceof String || update.getValue() instanceof Number) {
            val = update.getValue().toString();
        } else {
            val = JSON.toJSONString(update.getValue());
        }
        systemConfigPo.setValue(val);

        LambdaQueryWrapper<SystemConfigPo> query = new LambdaQueryWrapper<SystemConfigPo>()
                .eq(SystemConfigPo::getKey, update.getKey());
        systemConfigMapper.update(systemConfigPo, query);
        return R.ok("修改成功");
    }

    @Override
    public R<SystemConfigDetail> detail(Integer id) {
        return R.ok(systemConfigMapper.detail(id));
    }

    @Override
    public R<Page<SystemConfigBrief>> list(SystemConfigQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        return R.page(systemConfigMapper.list(query));
    }

    @Override
    public String getValue(String key) {
        return systemConfigMapper.getValue(key);
    }
}

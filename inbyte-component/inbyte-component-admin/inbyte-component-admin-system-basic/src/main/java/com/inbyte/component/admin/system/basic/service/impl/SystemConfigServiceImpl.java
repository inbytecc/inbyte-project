package com.inbyte.component.admin.system.basic.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.basic.model.*;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.basic.service.SystemConfigService;
import com.inbyte.component.admin.system.basic.dao.InbyteSystemConfigMapper;

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
    private InbyteSystemConfigMapper inbyteSystemConfigMapper;

    @Override
    public R insert(SystemConfigInsert insert) {
        LambdaQueryWrapper<InbyteSystemConfigPo> query = new LambdaQueryWrapper<InbyteSystemConfigPo>()
                .eq(InbyteSystemConfigPo::getKey, insert.getKey());
        Long l = inbyteSystemConfigMapper.selectCount(query);
        if (l > 0) {
            return R.error("配置项key已存在");
        }

        InbyteSystemConfigPo inbyteSystemConfigPo = InbyteSystemConfigPo.builder()
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, inbyteSystemConfigPo);
        inbyteSystemConfigMapper.insert(inbyteSystemConfigPo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer id) {
        inbyteSystemConfigMapper.deleteById(id);
        return R.ok("删除成功");
    }

    @Override
    public R update(SystemConfigUpdate update) {
        InbyteSystemConfigPo inbyteSystemConfigPo = InbyteSystemConfigPo.builder()
                .updateTime(LocalDateTime.now())
                .modifier(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, inbyteSystemConfigPo);

        inbyteSystemConfigMapper.updateById(inbyteSystemConfigPo);
        return R.ok("修改成功");
    }

    @Override
    public R updateByKey(SystemConfigUpdateByKey update) {
        InbyteSystemConfigPo inbyteSystemConfigPo = InbyteSystemConfigPo.builder()
                .updateTime(LocalDateTime.now())
                .modifier(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(update, inbyteSystemConfigPo);

        String val;
        if (update.getValue() instanceof String || update.getValue() instanceof Number) {
            val = update.getValue().toString();
        } else {
            val = JSON.toJSONString(update.getValue());
        }
        inbyteSystemConfigPo.setValue(val);

        inbyteSystemConfigMapper.insertOrUpdate(inbyteSystemConfigPo);

        return R.ok("修改成功");
    }

    @Override
    public R<SystemConfigDetail> detail(Integer id) {
        return R.ok(inbyteSystemConfigMapper.detail(id));
    }

    @Override
    public R<Page<SystemConfigBrief>> list(SystemConfigQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        return R.page(inbyteSystemConfigMapper.list(query));
    }

    @Override
    public String getValue(String key) {
        return inbyteSystemConfigMapper.getValue(key);
    }
}

package com.inbyte.component.admin.dict.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.component.admin.dict.dao.DictAdminMapper;
import com.inbyte.component.admin.dict.model.*;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.commons.model.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典服务
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Slf4j
@Service
public class DictAdminService {

    @Autowired
    private DictAdminMapper dictAdminMapper;

    public R insert(DictInsert insert) {
        SessionUtil.assertAdmin();

        DictPo dictPo = DictPo.builder()
                .createTime(LocalDateTime.now())
                .createUserId(SessionUtil.getUserId())
                .createUserName(SessionUtil.getUserName())
                .mctNo(SessionUtil.getDefaultMctNo())
                .build();
        BeanUtils.copyProperties(insert, dictPo);
        dictAdminMapper.insert(dictPo);
        return R.ok("新增成功");
    }

    public R delete(String dictId) {
        SessionUtil.assertAdmin();

        LambdaQueryWrapper<DictPo> queryWrapper = new LambdaQueryWrapper<DictPo>()
                .eq(DictPo::getDictId, dictId)
                .eq(DictPo::getMctNo, SessionUtil.getDefaultMctNo());
        dictAdminMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    public R update(DictUpdate update) {
        SessionUtil.assertAdmin();

        DictPo dictPo = DictPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, dictPo);

        LambdaQueryWrapper<DictPo> queryWrapper = new LambdaQueryWrapper<DictPo>()
                .eq(DictPo::getDictId, update.getDictId())
                .eq(DictPo::getMctNo, SessionUtil.getDefaultMctNo());
        dictAdminMapper.update(dictPo, queryWrapper);
        return R.ok("修改成功");
    }

    public R<DictDetail> detail(String dictId) {
        return R.ok(dictAdminMapper.detail(dictId));
    }

    public R<List<DictBrief>> list(DictQuery query) {
        return R.ok(dictAdminMapper.list(query));
    }

}

package com.inbyte.component.admin.aliyun.oss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inbyte.component.admin.aliyun.oss.dao.ObjectStorageMapper;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.*;
import com.inbyte.component.admin.aliyun.oss.service.ObjectStorageService;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 对象存储服务
 *
 * @author chenjw
 * @date 2023-03-14 14:25:55
 **/
@Service
public class ObjectStorageServiceImpl implements ObjectStorageService {

    @Autowired
    private ObjectStorageMapper objectstorageMapper;

    @Override
    public R<ObjectStorageDetail> detail(Integer objectId) {
        return R.ok(objectstorageMapper.detail(objectId));
    }

    @Override
    public R<Page<ObjectStorageBrief>> list(ObjectStorageQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        return R.page(objectstorageMapper.list(SessionUtil.getDefaultMctNo(), query));
    }

    @Override
    public R update(ObjectStorageUpdate update) {
        LambdaQueryWrapper<ObjectStoragePo> wrapper = new QueryWrapper<ObjectStoragePo>().lambda();
        wrapper.eq(ObjectStoragePo::getObjectId, update.getObjectId())
                .eq(ObjectStoragePo::getMctNo, SessionUtil.getDefaultMctNo());

        ObjectStoragePo objectstoragePo = ObjectStoragePo.builder()
                .remark(update.getRemark())
                .updateTime(LocalDateTime.now())
                .build();
        objectstorageMapper.update(objectstoragePo, wrapper);
        return R.ok("修改成功");
    }

}

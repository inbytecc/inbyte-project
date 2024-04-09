package com.inbyte.component.app.aliyun.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.aliyun.oss.model.storage.ObjectStorageBrief;
import com.inbyte.component.app.aliyun.oss.model.storage.ObjectStorageDetail;
import com.inbyte.component.app.aliyun.oss.model.storage.ObjectStoragePo;
import com.inbyte.component.app.aliyun.oss.model.storage.ObjectStorageQuery;

import java.util.List;

/**
 * 对象存储
 *
 * 表名：  object_storage
 * @author chenjw
 * @date 2023-05-10 14:53:36
 */
public interface ObjectStorageMapper extends BaseMapper<ObjectStoragePo> {

    /**
     * 详情
     *
     * @param objectId
     * @return ObjectStorageDetail
     **/
    ObjectStorageDetail detail(Integer objectId);

    /**
     * 查询列表
     * @param query
     * @return List<ObjectStorageBrief>
     **/
    List<ObjectStorageBrief> list(ObjectStorageQuery query);
}

package com.inbyte.component.admin.aliyun.oss.service;

import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageBrief;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageDetail;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageQuery;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageUpdate;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;

import java.util.List;

/**
 * 对象存储服务
 *
 * @author chenjw
 * @date 2023-03-14 14:25:55
 **/
public interface ObjectStorageService {

    /**
     * 详情
     *
     * @param objectId
     * @return ObjectStorageDetail
     **/
    R<ObjectStorageDetail> detail(Integer objectId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<ObjectStorageBrief>>>
     **/
    R<Page<List<ObjectStorageBrief>>> list(ObjectStorageQuery query);

    R update(ObjectStorageUpdate update);
}

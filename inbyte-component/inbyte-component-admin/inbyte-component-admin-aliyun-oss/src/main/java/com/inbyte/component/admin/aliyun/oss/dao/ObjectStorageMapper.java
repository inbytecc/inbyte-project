package com.inbyte.component.admin.aliyun.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageBrief;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageDetail;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.InbyteObjectStoragePo;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 对象存储
 *
 * 表名：  inbyte_object_storage
 * @author chenjw
 * @date 2023-03-14 14:25:55
 */
public interface ObjectStorageMapper extends BaseMapper<InbyteObjectStoragePo> {

//    /**
//     * 概要
//     *
//     * @param objectId
//     * @return ObjectStorageBrief
//     **/
//    ObjectStorageBrief brief(Integer objectId);

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
    List<ObjectStorageBrief> list(@Param("mctNo") String mctNo,
                                  @Param("query") ObjectStorageQuery query);
}

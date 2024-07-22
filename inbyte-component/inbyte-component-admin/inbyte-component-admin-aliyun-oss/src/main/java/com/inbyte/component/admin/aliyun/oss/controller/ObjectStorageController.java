package com.inbyte.component.admin.aliyun.oss.controller;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageBrief;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageDetail;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageQuery;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.ObjectStorageUpdate;
import com.inbyte.component.admin.aliyun.oss.service.ObjectStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对象存储
 *
 * @author chenjw
 * @date 2023-03-14 14:25:55
 **/
@RestController
@RequestMapping("object/storage")
public class ObjectStorageController {

    @Autowired
    private ObjectStorageService objectstorageService;

    /**
     * 详情
     *
     * @param objectId
     * @return Result<ObjectStorageDetail>
     **/
    @GetMapping("{objectId}")
    public R<ObjectStorageDetail> detail(@PathVariable("objectId") Integer objectId) {
        return objectstorageService.detail(objectId);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid ObjectStorageUpdate update) {
        return objectstorageService.update(update);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<ObjectStorageBrief>>>
     **/
    @GetMapping
    public R<Page<ObjectStorageBrief>> list(@ModelAttribute @Valid ObjectStorageQuery query) {
        return objectstorageService.list(query);
    }
}

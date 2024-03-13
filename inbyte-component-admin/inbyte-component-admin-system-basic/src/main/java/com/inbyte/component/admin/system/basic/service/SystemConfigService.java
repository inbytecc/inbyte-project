package com.inbyte.component.admin.system.basic.service;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.basic.model.*;

/**
 * 系统配置服务
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
public interface SystemConfigService {

    /**
     * 新增
     *
     * @param insert
     * @return R
     **/
    R insert(SystemConfigInsert insert);

    /**
     * 删除
     *
     * @param id
     * @return R
     **/
    R delete(Integer id);

    /**
     * 修改
     *
     * @param update
     * @return R
     **/
    R update(SystemConfigUpdate update);

    R updateByKey(SystemConfigUpdateByKey update);

    /**
     * 详情
     *
     * @param id
     * @return SystemConfigDetail
     **/
    R<SystemConfigDetail> detail(Integer id);

    /**
     * 列表
     *
     * @param query
     * @return R<Page<SystemConfigBrief>>
     **/
    R<Page<SystemConfigBrief>> list(SystemConfigQuery query);

    String getValue(String key);
}

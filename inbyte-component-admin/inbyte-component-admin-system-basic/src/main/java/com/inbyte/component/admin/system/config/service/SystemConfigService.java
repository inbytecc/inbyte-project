package com.inbyte.component.admin.system.config.service;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.config.model.SystemConfigQuery;
import com.inbyte.component.admin.system.config.model.SystemConfigInsert;
import com.inbyte.component.admin.system.config.model.SystemConfigUpdate;
import com.inbyte.component.admin.system.config.model.SystemConfigBrief;
import com.inbyte.component.admin.system.config.model.SystemConfigDetail;

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
}

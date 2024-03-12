package com.inbyte.component.admin.system.basic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.basic.model.SystemConfigPo;
import com.inbyte.component.admin.system.basic.model.SystemConfigQuery;
import com.inbyte.component.admin.system.basic.model.SystemConfigBrief;
import com.inbyte.component.admin.system.basic.model.SystemConfigDetail;
import java.util.List;

/**
 * 系统配置
 *
 * 表名 system_config
 * @author chenjw
 * @date 2024-03-11 11:07:53
 */
public interface SystemConfigMapper extends BaseMapper<SystemConfigPo> {

    /**
     * 详情
     *
     * @param id
     * @return SystemConfigDetail
     **/
    SystemConfigDetail detail(Integer id);

    /**
     * 查询列表
     * @param query
     * @return List<SystemConfigBrief>
     **/
    List<SystemConfigBrief> list(SystemConfigQuery query);
}

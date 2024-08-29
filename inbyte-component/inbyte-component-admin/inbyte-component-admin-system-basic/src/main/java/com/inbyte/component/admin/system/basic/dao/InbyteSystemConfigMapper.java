package com.inbyte.component.admin.system.basic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.basic.model.InbyteSystemConfigPo;
import com.inbyte.component.admin.system.basic.model.SystemConfigQuery;
import com.inbyte.component.admin.system.basic.model.SystemConfigBrief;
import com.inbyte.component.admin.system.basic.model.SystemConfigDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统配置
 *
 * 表名 inbyte_system_config
 * @author chenjw
 * @date 2024-03-11 11:07:53
 */
public interface InbyteSystemConfigMapper extends BaseMapper<InbyteSystemConfigPo> {

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

    @Select("SELECT `value` " +
            "  FROM inbyte_system_config" +
            " WHERE `key` = #{key}")
    String getValue(@Param("key") String key);

    @Insert("INSERT INTO inbyte_system_config (`key`, `value`, `remark`, `deleted`, `open`, `creator`,  `create_time`) " +
            "VALUES (#{key}, #{value}, #{remark}, 0, 0, #{modifier}, {updateTime}) " +
            "    ON DUPLICATE KEY UPDATE " +
            "`value` = VALUES(`value`), " +
            "`remark` = VALUES(`remark`), " +
            "`modifier` = VALUES(`modifier`), " +
            "`update_time` = {updateTime}")
    int insertOrUpdate(InbyteSystemConfigPo insert);
}

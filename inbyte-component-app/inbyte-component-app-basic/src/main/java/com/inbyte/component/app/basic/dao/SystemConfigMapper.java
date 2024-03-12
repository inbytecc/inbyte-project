package com.inbyte.component.app.basic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.basic.model.SystemConfigPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cache.decorators.FifoCache;

/**
 * 系统配置
 * <p>
 * 表名 system_config
 *
 * @author chenjw
 * @date 2024-03-11 11:07:53
 */
@CacheNamespace(
        eviction = FifoCache.class,
        flushInterval = 60000, // 1分钟刷新
        size = 1024,
        readWrite = false
)
public interface SystemConfigMapper extends BaseMapper<SystemConfigPo> {

    @Select("SELECT `value` " +
            "  FROM system_config" +
            " WHERE `key` = #{key}")
    String getValue(@Param("key") String key);
}

package com.inbyte.component.common.weixin.enterprise.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.common.weixin.enterprise.model.InbyteNoticeEnterpriseWeixinPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 消息通知 - 企微
 *
 * 表名 inbyte_notice_enterprise_weixin
 * @author chenjw
 * @date 2024-05-24 14:47:44
 */
public interface InbyteNoticeEnterpriseWeixinMapper extends BaseMapper<InbyteNoticeEnterpriseWeixinPo> {

    @Select("SELECT default_robot_id FROM ")
    String getRobot(@Param("venueId") String venueId);
}

package com.inbyte.component.admin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.user.model.data.UserLocationBrief;
import com.inbyte.component.admin.user.model.data.UserLocationPo;
import com.inbyte.component.admin.user.model.data.UserLocationQuery;

import java.util.List;

/**
 * 用户定位
 *
 * 表名：  user_location
 * @author chenjw
 * @date 2023-03-29 11:27:58
 */
public interface UserLocationMapper extends BaseMapper<UserLocationPo> {

    /**
     * 查询列表
     * @param query
     * @return List<UserLocationBrief>
     **/
    List<UserLocationBrief> list(UserLocationQuery query);
}

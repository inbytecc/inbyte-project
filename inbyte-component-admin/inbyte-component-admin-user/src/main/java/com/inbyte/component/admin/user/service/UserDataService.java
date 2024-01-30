package com.inbyte.component.admin.user.service;

import com.inbyte.component.admin.user.model.data.UserLocationBrief;
import com.inbyte.component.admin.user.model.data.UserLocationQuery;
import com.inbyte.component.admin.user.model.data.UserTrendQuery;
import com.inbyte.component.admin.user.model.mp.UserTrendBrief;
import com.inbyte.commons.model.dto.R;

import java.util.List;

/**
 * 用户定位服务
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
public interface UserDataService {

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<UserLocationBrief>>>
     **/
    R<List<UserLocationBrief>> userDistribution(UserLocationQuery query);

    R<List<UserTrendBrief>> userTrend(UserTrendQuery query);
}

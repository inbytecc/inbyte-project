package com.inbyte.component.admin.user.service.impl;

import com.inbyte.component.admin.user.model.data.UserLocationBrief;
import com.inbyte.component.admin.user.model.data.UserLocationQuery;
import com.inbyte.component.admin.user.model.data.UserTrendQuery;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.user.dao.UserLocationMapper;
import com.inbyte.component.admin.user.dao.UserWeixinMpMapper;
import com.inbyte.component.admin.user.model.mp.UserTrendBrief;
import com.inbyte.component.admin.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户定位服务
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@Service
public class DataCenterUserDataServiceImpl implements UserDataService {

    @Autowired
    private UserLocationMapper userLocationMapper;
    @Autowired
    private UserWeixinMpMapper userWeixinMpMapper;

    @Override
    public R<List<UserLocationBrief>> userDistribution(UserLocationQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        return R.success(userLocationMapper.list(query));
    }

    @Override
    public R<List<UserTrendBrief>> userTrend(UserTrendQuery query) {
        return R.success(userWeixinMpMapper.userTrend(query));
    }
}

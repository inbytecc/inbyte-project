package com.inbyte.component.admin.user.service;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.user.model.mp.UserWeixinMpBrief;
import com.inbyte.component.admin.user.model.mp.UserWeixinMpDetail;
import com.inbyte.component.admin.user.model.mp.UserWeixinMpQuery;
import com.inbyte.component.admin.user.model.mp.UserWeixinMpUpdate;

import java.util.List;

/**
 * 微信小程序用户服务
 *
 * @author chenjw
 * @date 2023-08-08 15:08:40
 **/
public interface UserWeixinMpService {

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(UserWeixinMpUpdate update);

    /**
     * 详情
     *
     * @param eid
     * @return UserWeixinMpDetail
     **/
    R<UserWeixinMpDetail> detail(Integer eid);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<UserWeixinMpBrief>>>
     **/
    R<Page<UserWeixinMpBrief>> list(UserWeixinMpQuery query);
}

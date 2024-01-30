package com.inbyte.component.admin.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.user.dao.UserWeixinMpMapper;
import com.inbyte.component.admin.user.model.mp.*;
import com.inbyte.component.admin.user.service.UserWeixinMpService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信小程序用户服务
 *
 * @author chenjw
 * @date 2023-08-08 15:08:40
 **/
@Service
public class UserWeixinMpServiceImpl implements UserWeixinMpService {

    @Autowired
    private UserWeixinMpMapper userWeixinMpMapper;

    @Override
    public R update(UserWeixinMpUpdate update) {
        LambdaUpdateWrapper<UserWeixinMpPo> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(UserWeixinMpPo::getEid, update.getEid());
        updateWrapper.eq(UserWeixinMpPo::getMctNo, SessionUtil.getDefaultMctNo());
        updateWrapper.set(UserWeixinMpPo::getRemark, update.getRemark());
        userWeixinMpMapper.update(null, updateWrapper);
        return R.success("修改成功");
    }

    @Override
    public R<UserWeixinMpDetail> detail(Integer eid) {
        return R.success(userWeixinMpMapper.detail(eid, SessionUtil.getDefaultMctNo()));
    }

    @Override
    public R<Page<List<UserWeixinMpBrief>>> list(UserWeixinMpQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getDefaultMctNo());
        return R.page(userWeixinMpMapper.list(query));
    }
}

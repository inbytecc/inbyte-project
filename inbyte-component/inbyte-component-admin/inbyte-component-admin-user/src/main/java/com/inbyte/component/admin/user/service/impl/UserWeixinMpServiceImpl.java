package com.inbyte.component.admin.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.ArithUtil;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.user.dao.UserWeixinMpMapper;
import com.inbyte.component.admin.user.model.data.UserWeixinStat;
import com.inbyte.component.admin.user.model.mp.weixin.*;
import com.inbyte.component.admin.user.service.UserWeixinMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        updateWrapper.eq(UserWeixinMpPo::getMctNo, SessionUtil.getMctNo());
        updateWrapper.set(UserWeixinMpPo::getRemark, update.getRemark());
        userWeixinMpMapper.update(null, updateWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<UserWeixinMpDetail> detail(Integer eid) {
        return R.ok(userWeixinMpMapper.detail(eid, SessionUtil.getMctNo()));
    }

    @Override
    public R<Page<UserWeixinMpBrief>> list(UserWeixinMpQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(userWeixinMpMapper.list(query));
    }

    @Override
    public UserWeixinStat getUserStats() {
        UserWeixinStat userStats = new UserWeixinStat();
        String mctNo = SessionUtil.getMctNo();
        // Calculate daily growth percentage
        int totalUsersStartOfDay = userWeixinMpMapper.getTotalUsersStartOfDay(mctNo);
        int totalUsersEndOfDay = userWeixinMpMapper.getTotalUsersEndOfDay(mctNo);
        BigDecimal dailyGrowth = calculateGrowthPercentage(totalUsersStartOfDay, totalUsersEndOfDay);

        // Calculate weekly growth percentage
        int totalUsersStartOfWeek = userWeixinMpMapper.getTotalUsersStartOfWeek(mctNo);
        int totalUsersEndOfWeek = userWeixinMpMapper.getTotalUsersEndOfWeek(mctNo);
        BigDecimal weeklyGrowth = calculateGrowthPercentage(totalUsersStartOfWeek, totalUsersEndOfWeek);

        // Calculate monthly growth percentage
        int totalUsersStartOfMonth = userWeixinMpMapper.getTotalUsersStartOfMonth(mctNo);
        int totalUsersEndOfMonth = userWeixinMpMapper.getTotalUsersEndOfMonth();
        BigDecimal monthlyGrowth = calculateGrowthPercentage(totalUsersStartOfMonth, totalUsersEndOfMonth);

        // Assuming we want the current total users
        userStats.setTotalUsers(totalUsersEndOfDay);
        userStats.setDailyGrowth(dailyGrowth);
        userStats.setWeeklyGrowth(weeklyGrowth);
        userStats.setMonthlyGrowth(monthlyGrowth);

        return userStats;
    }

    private BigDecimal calculateGrowthPercentage(int startValue, int endValue) {
        if (startValue == 0) {
            return new BigDecimal("100"); // Handle division by zero
        }
        return ArithUtil.multiply(ArithUtil.divide(BigDecimal.valueOf(endValue - startValue), startValue),
                new BigDecimal("100"));
    }
}

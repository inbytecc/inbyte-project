package com.inbyte.component.admin.user.model.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserWeixinStat {
    private int totalUsers;
    private BigDecimal dailyGrowth;
    private BigDecimal weeklyGrowth;
    private BigDecimal monthlyGrowth;
    private int newUsers;
    private int message;
    private int money;
    private int shopping;

}
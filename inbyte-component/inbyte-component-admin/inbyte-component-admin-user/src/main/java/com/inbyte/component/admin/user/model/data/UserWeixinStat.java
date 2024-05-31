package com.inbyte.component.admin.user.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWeixinStat {
    private int totalUsers;
    private double dailyGrowth;
    private double weeklyGrowth;
    private double monthlyGrowth;
    private int newUsers;
    private int message;
    private int money;
    private int shopping;

}
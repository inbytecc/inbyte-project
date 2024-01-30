package com.inbyte.component.admin.user.controller;

import com.inbyte.component.admin.user.model.data.UserLocationBrief;
import com.inbyte.component.admin.user.model.data.UserLocationQuery;
import com.inbyte.component.admin.user.model.data.UserTrendQuery;
import com.inbyte.component.admin.user.model.mp.UserTrendBrief;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.user.service.UserDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据中心 - 用户
 *
 * @author chenjw
 * @date 2023-03-29 11:27:58
 **/
@RestController
@RequestMapping("data-center/user")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    /**
     * 用户分布
     *
     * @param query
     * @return Result<List<UserLocationBrief>>
     **/
    @GetMapping("location")
    public R<List<UserLocationBrief>> userDistribution(@ModelAttribute @Valid UserLocationQuery query) {
        return userDataService.userDistribution(query);
    }

    /**
     * 用户增长趋势
     * 默认查询最近一个月
     * @param query
     * @return Result<List<UserLocationBrief>>
     **/
    @GetMapping("trend")
    public R<List<UserTrendBrief>> userTrend(@ModelAttribute @Valid UserTrendQuery query) {
        return userDataService.userTrend(query);
    }
}

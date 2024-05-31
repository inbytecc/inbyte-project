package com.inbyte.component.admin.user.controller;

import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.user.model.data.UserStatsDTO;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpBrief;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpDetail;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpQuery;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpUpdate;
import com.inbyte.component.admin.user.service.UserWeixinMpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序用户
 *
 * @author chenjw
 * @date 2023-08-08 15:08:40
 **/
@RestController
@RequestMapping("user/weixin/mp")
public class UserWeixinMpController {

    @Autowired
    private UserWeixinMpService userWeixinMpService;

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid UserWeixinMpUpdate update) {
        return userWeixinMpService.update(update);
    }

    /**
     * 详情
     *
     * @param eid
     * @return Result<UserWeixinMpDetail>
     **/
    @GetMapping("{eid}")
    public R<UserWeixinMpDetail> detail(@PathVariable("eid") Integer eid) {
        return userWeixinMpService.detail(eid);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<UserWeixinMpBrief>>>
     **/
    @GetMapping
    public R<Page<UserWeixinMpBrief>> list(@ModelAttribute @Valid UserWeixinMpQuery query) {
        return userWeixinMpService.list(query);
    }

    @GetMapping("/stat")
    public R<UserStatsDTO> getUserStats() {
        return R.ok(userWeixinMpService.getUserStats());
    }
}

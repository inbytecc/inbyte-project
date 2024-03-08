package com.inbyte.component.admin.user.controller;

import com.inbyte.component.admin.user.model.*;
import com.inbyte.component.admin.user.service.UserService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid UserUpdate update) {
        return userService.update(update);
    }

    /**
     * 详情
     *
     * @param userId
     * @return Result<UserDetail>
     **/
    @GetMapping("{userId}")
    public R<UserDetail> detail(@PathVariable("userId") Integer userId) {
        return userService.detail(userId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<UserBrief>>>
     **/
    @GetMapping
    public R<Page<UserBrief>> list(@ModelAttribute @Valid UserQuery query) {
        return userService.list(query);
    }

    @GetMapping("dict")
    public R<Page<UserDictBrief>> dict(UserQuery query) {
        return userService.dict(query);
    }

}

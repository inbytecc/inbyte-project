package com.inbyte.component.admin.system.user.controller;

import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.user.*;
import com.inbyte.component.admin.system.user.model.system.user.merchant.InbyteSystemUserMerchantBrief;
import com.inbyte.component.admin.system.user.service.SystemUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商户用户
 *
 * @author chenjw
 * @date 2022-11-01 10:22:12
 **/
@RestController
@RequestMapping("system/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 手机号登录
     * <p>
     * 手机号加密码登录
     *
     * @param param
     * @return
     */
    @PostMapping("login/id-pwd")
    public R<SystemUserLoginDto> idPwdLogin(@RequestBody @Valid SystemUserLoginParam param) {
        return systemUserService.idPwdLogin(param);
    }

    /**
     * 个人信息
     * <p>
     * 登录用户信息
     *
     * @return
     */
    @GetMapping("info")
    public R<SystemUserInfo> info() {
        return systemUserService.info();
    }


    /**
     * 字典
     *
     * @param keyword
     * @return Result<Dict>
     **/
    @GetMapping("dict")
    public R<List<Dict>> dict(@RequestParam(value = "keyword", required = false) String keyword) {
        return systemUserService.dict(keyword);
    }

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid SystemUserInsert insert) {
        return systemUserService.insert(insert);
    }

    /**
     * 删除
     *
     * @param userId
     * @return Result
     **/
    @DeleteMapping("{userId}")
    public R delete(@PathVariable("userId") Integer userId) {
        return systemUserService.delete(userId);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid SystemUserUpdate update) {
        return systemUserService.update(update);
    }

    /**
     * 重置指定用户密码
     * <p>
     * 重置密码为123456
     *
     * @param userId
     * @return Result
     **/
    @PutMapping("{userId}/reset-pwd")
    public R resetPwd(@PathVariable("userId") Integer userId) {
        return systemUserService.resetPwd(userId);
    }

    /**
     * 修改当前账号密码
     *
     * @param update
     * @return Result
     **/
    @PutMapping("pwd")
    public R updatePwd(@RequestBody @Valid SystemUserPwdUpdate update) {
        return systemUserService.updatePwd(update);
    }

    /**
     * 详情
     *
     * @param userId
     * @return Result<UserPlatformDetail>
     **/
    @GetMapping("{userId}")
    public R<SystemUserDetail> detail(@PathVariable("userId") Integer userId) {
        return systemUserService.detail(userId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page < List < UserPlatformBrief>>>
     **/
    @GetMapping
    public R<Page<SystemUserBrief>> list(@ModelAttribute @Valid SystemUserQuery query) {
        return systemUserService.list(query);
    }

    /**
     * 切换商户
     *
     * @param mctNo
     * @return
     */
    @GetMapping("merchant/{mctNo}/switch")
    public R switchMerchant(@PathVariable String mctNo) {
        return systemUserService.switchMerchant(mctNo);
    }

    @GetMapping("merchant")
    public R<List<InbyteSystemUserMerchantBrief>> switchMerchant() {
        return systemUserService.merchantList();
    }
}

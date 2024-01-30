package com.inbyte.component.app.user.wallet.controller;
import com.inbyte.component.app.user.wallet.service.UserWalletService;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletRechargeBrief;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.inbyte.commons.model.dto.R;

import java.util.List;

/**
 * 用户钱包
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 **/
@RestController
@RequestMapping("user/wallet")
public class UserWalletController {

    @Autowired
    private UserWalletService userWalletService;

    /**
     * 我的钱包
     *
     * @return Result<UserWalletDetail>
     **/
    @GetMapping("mine")
    public R<UserWalletDetail> detail() {
        return userWalletService.detail();
    }

    /**
     * 充值配置
     *
     * @return Result<List<UserWalletBrief>>
     **/
    @GetMapping("recharge-config")
    public R<List<UserWalletRechargeBrief>> list() {
        return userWalletService.list();
    }
}

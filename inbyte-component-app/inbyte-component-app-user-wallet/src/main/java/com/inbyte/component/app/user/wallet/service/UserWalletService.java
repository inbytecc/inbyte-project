package com.inbyte.component.app.user.wallet.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletRechargeBrief;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletDetail;

import java.util.List;

/**
 * 用户钱包服务
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 **/
public interface UserWalletService {

    /**
     * 详情
     *
     * @return UserWalletDetail
     **/
    R<UserWalletDetail> detail();

    /**
     * 列表
     *
     * @return Result<List<UserWalletBrief>>
     **/
    R<List<UserWalletRechargeBrief>> list();
}

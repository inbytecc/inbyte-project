package com.inbyte.component.app.user.wallet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletDetail;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletPo;

/**
 * 用户钱包
 * <p>
 * 表名：  user_wallet
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 */
public interface UserWalletMapper extends BaseMapper<UserWalletPo> {

    /**
     * 详情
     *
     * @param walletId
     * @return UserWalletDetail
     **/
    UserWalletDetail detail(Integer walletId);

}

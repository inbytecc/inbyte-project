package com.inbyte.component.app.user.wallet.service.impl;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.wallet.dao.UserWalletMapper;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletPo;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletRechargeBrief;
import com.inbyte.component.app.user.wallet.model.user.wallet.UserWalletDetail;
import com.inbyte.component.app.user.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户钱包服务
 *
 * @author chenjw
 * @date 2023-12-23 10:31:06
 **/
@Service
public class UserWalletServiceImpl implements UserWalletService {

    @Autowired
    private UserWalletMapper userWalletMapper;


    @Override
    public R<UserWalletDetail> detail() {
        UserWalletDetail detail = userWalletMapper.detail(SessionUtil.getUserId());
        if (detail == null) {
            detail = new UserWalletDetail();
            detail.setBalance(BigDecimal.ZERO);
            UserWalletPo userWalletPo = UserWalletPo.builder()
                    .userId(SessionUtil.getUserId())
                    .balance(BigDecimal.ZERO)
                    .createdTime(LocalDateTime.now())
                    .build();
            userWalletMapper.insert(userWalletPo);
        }
        return R.success(detail);
    }

    @Override
    public R<List<UserWalletRechargeBrief>> list() {
        return R.success();
    }
}

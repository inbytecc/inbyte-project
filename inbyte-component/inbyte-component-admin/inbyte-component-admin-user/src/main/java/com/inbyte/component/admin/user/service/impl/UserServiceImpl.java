package com.inbyte.component.admin.user.service.impl;

import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.admin.user.dao.UserMapper;
import com.inbyte.component.admin.user.model.*;
import com.inbyte.component.admin.user.service.UserService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户服务
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public R update(UserUpdate update) {
        UserPo userPo = UserPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, userPo);
        userMapper.updateById(userPo);
        return R.ok("修改成功");
    }

    @Override
    public R<UserDetail> detail(Integer userId) {
        return R.ok(userMapper.detail(userId));
    }

    @Override
    public R<Page<UserBrief>> list(UserQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        return R.page(userMapper.list(query));
    }

    @Override
    public R<Page<UserDictBrief>> dict(UserQuery query) {
        PageUtil.startPage(query);
        return R.page(userMapper.dict(query));
    }

    @Override
    public Integer register(String tel) {
        if (StringUtil.isEmpty(tel)) {
            throw InbyteException.failure("请输入手机号");
        }
        if (tel.length() != 11) {
            throw InbyteException.failure("手机号长度不正确");
        }
        UserBrief userBrief = userMapper.briefByTel(tel);
        if (userBrief != null) {
            return userBrief.getUserId();
        }

        String nickname = "用户" + tel.substring(7);
        UserPo userPo = UserPo.builder()
                .tel(tel)
                .nickname(nickname)
                .userName(nickname)
                .avatar("https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132")
                .createTime(LocalDateTime.now())
                .build();
        userMapper.insert(userPo);
        return userPo.getUserId();
    }
}

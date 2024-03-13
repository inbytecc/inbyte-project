package com.inbyte.component.admin.user.service.impl;

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
import java.util.List;

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
    public R<Integer> register(String tel) {
        UserBrief userBrief = userMapper.briefByTel(tel);
        if (userBrief != null) {
            return R.ok("用户已注册", userBrief.getUserId());
        }

        String nickName = "用户" + tel.substring(7);
        UserPo userPo = UserPo.builder()
                .tel(tel)
                .nickName(nickName)
                .userName(nickName)
                .avatar("https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132")
                .createTime(LocalDateTime.now())
                .build();
        userMapper.insert(userPo);
        return R.ok("注册成功", userPo.getUserId());
    }
}

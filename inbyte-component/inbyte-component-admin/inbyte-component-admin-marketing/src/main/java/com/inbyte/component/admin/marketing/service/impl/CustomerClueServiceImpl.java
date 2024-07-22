package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.component.admin.marketing.dao.CustomerClueMapper;
import com.inbyte.component.admin.marketing.model.clue.*;
import com.inbyte.component.admin.marketing.service.CustomerClueService;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dao.InbyteSystemUserMapper;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserDetail;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 客户线索服务
 *
 * @author chenjw
 * @date 2023-03-09 13:17:26
 **/
@Service
public class CustomerClueServiceImpl implements CustomerClueService {

    @Autowired
    private CustomerClueMapper customerclueMapper;
    @Autowired
    private InbyteSystemUserMapper inbyteSystemUserMapper;

    @Override
    public R insert(CustomerClueInsert insert) {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        CustomerCluePo customercluePo = CustomerCluePo.builder()
                .mctNo(sessionUser.getMctNo())
                .createTime(LocalDateTime.now())
                .creator(sessionUser.getUserName())
                .build();
        BeanUtils.copyProperties(insert, customercluePo);
        if (insert.getContactPersonId() != null) {
            SystemUserDetail detail = inbyteSystemUserMapper.detail(insert.getContactPersonId());
            customercluePo.setContactPersonName(detail.getUserName());
        }
        customerclueMapper.insert(customercluePo);
        return R.ok("新增成功");
    }

    @Override
    public R update(CustomerClueUpdate update) {
        CustomerCluePo customercluePo = CustomerCluePo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, customercluePo);

        LambdaUpdateWrapper<CustomerCluePo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CustomerCluePo::getClueId, update.getClueId());
        updateWrapper.eq(CustomerCluePo::getMctNo, SessionUtil.getMctNo());
        customerclueMapper.update(customercluePo, updateWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<CustomerClueDetail> detail(Integer clueId) {
        return R.ok(customerclueMapper.detail(clueId));
    }

    @Override
    public R<Page<CustomerClueBrief>> list(CustomerClueQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(customerclueMapper.list(query));
    }
}

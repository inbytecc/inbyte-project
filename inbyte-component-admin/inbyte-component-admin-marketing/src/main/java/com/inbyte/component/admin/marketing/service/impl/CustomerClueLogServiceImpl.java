package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.component.admin.marketing.dao.CustomerClueLogMapper;
import com.inbyte.component.admin.marketing.dao.CustomerClueMapper;
import com.inbyte.component.admin.marketing.dict.ContactStageDict;
import com.inbyte.component.admin.marketing.model.clue.CustomerCluePo;
import com.inbyte.component.admin.marketing.model.clue.log.*;
import com.inbyte.component.admin.marketing.service.CustomerClueLogService;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 跟进记录服务
 *
 * @author chenjw
 * @date 2023-03-09 15:56:58
 **/
@Service
public class CustomerClueLogServiceImpl implements CustomerClueLogService {

    @Autowired
    private CustomerClueMapper customerclueMapper;
    @Autowired
    private CustomerClueLogMapper customerClueLogMapper;

    @Override
    public R insert(CustomerClueLogInsert insert) {
        CustomerCluePo customerCluePo = customerclueMapper.selectById(insert.getClueId());
        if (customerCluePo == null) {
            return R.failure("客户线索不存在");
        }

        LocalDateTime now = LocalDateTime.now();
        SessionUser sessionUser = SessionUtil.getSessionUser();
        CustomerClueLogPo customerClueLogPo = CustomerClueLogPo.builder()
                .mctNo(sessionUser.getMctNo())
                .createTime(LocalDateTime.now())
                .createUserId(sessionUser.getUserId())
                .createUserName(sessionUser.getUserName())
                .build();
        BeanUtils.copyProperties(insert, customerClueLogPo);
        customerClueLogMapper.insert(customerClueLogPo);

        CustomerCluePo cluePo = CustomerCluePo.builder()
                .clueId(insert.getClueId())
                .latestContactTime(LocalDateTime.now())
                .latestContactMsg(insert.getRemark())
                .nextContactTime(insert.getNextContactTime())
                .contactStage(insert.getContactStage())
                .build();
        if (customerCluePo.getContactPersonId() == null) {
            cluePo.setContactPersonId(sessionUser.getUserId());
            cluePo.setContactPersonName(sessionUser.getUserName());
        }
        if (insert.getContactStage() == ContactStageDict.Made_A_Deal.code) {
            cluePo.setConverted(WhetherDict.Yes.code);
            cluePo.setConvertTime(now);
        } else if (insert.getContactStage() == ContactStageDict.Abandon.code) {
            cluePo.setDropped(WhetherDict.Yes.code);
            cluePo.setDropReason(insert.getRemark());
            cluePo.setDropTime(now);
        } else if (insert.getContactStage() == ContactStageDict.Trial_Class_Booked.code) {
            cluePo.setAppointment(WhetherDict.Yes.code);
            cluePo.setAppointmentTime(now);
        } else if (insert.getContactStage() == ContactStageDict.Trial_Class_Finished.code) {
            cluePo.setTrialed(WhetherDict.Yes.code);
            cluePo.setTrailTime(now);
        }

        LambdaQueryWrapper<CustomerCluePo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(CustomerCluePo::getClueId, insert.getClueId());
        queryWrapper.eq(CustomerCluePo::getMctNo, sessionUser.getMctNo());
        customerclueMapper.update(cluePo, queryWrapper);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer logId) {
        LambdaQueryWrapper<CustomerClueLogPo> deleteWrapper = new LambdaQueryWrapper();
        deleteWrapper.eq(CustomerClueLogPo::getLogId, logId);
        deleteWrapper.eq(CustomerClueLogPo::getMctNo, SessionUtil.getDefaultMctNo());
        customerClueLogMapper.delete(deleteWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(CustomerClueLogUpdate update) {
        CustomerClueLogPo customerClueLogPo = CustomerClueLogPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, customerClueLogPo);
        customerClueLogMapper.updateById(customerClueLogPo);
        return R.ok("修改成功");
    }

    @Override
    public R<CustomerClueLogDetail> detail(Integer logId) {
        return R.ok(customerClueLogMapper.detail(logId));
    }

    @Override
    public R<Page<List<CustomerClueLogBrief>>> list(CustomerClueLogQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getDefaultMctNo());
        return R.page(customerClueLogMapper.list(query));
    }
}

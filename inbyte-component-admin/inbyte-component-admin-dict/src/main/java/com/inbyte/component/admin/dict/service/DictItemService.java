package com.inbyte.component.admin.dict.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.component.admin.dict.dao.DictItemMapper;
import com.inbyte.component.admin.dict.model.dict.item.*;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典项服务
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Service
public class DictItemService {

    @Autowired
    private DictItemMapper dictItemMapper;

    public R insert(DictItemInsert insert) {
        SessionUtil.assertAdmin();

        LambdaQueryWrapper<DictItemPo> queryWrapper = new LambdaQueryWrapper<DictItemPo>()
                .eq(DictItemPo::getDictId, insert.getDictId())
                .eq(DictItemPo::getMctNo, SessionUtil.getDefaultMctNo());
        Integer count = Math.toIntExact(dictItemMapper.selectCount(queryWrapper));

        DictItemPo dictItemPo = DictItemPo.builder()
                .createTime(LocalDateTime.now())
                .creatorId(SessionUtil.getUserId())
                .creatorName(SessionUtil.getUserName())
                .mctNo(SessionUtil.getDefaultMctNo())
                .build();
        BeanUtils.copyProperties(insert, dictItemPo);

        dictItemPo.setCode(count);
        dictItemPo.setOrdinal(BigDecimal.valueOf(count));
        dictItemMapper.insert(dictItemPo);
        return R.ok("新增成功");
    }

    public R delete(Integer itemId) {
        SessionUtil.assertAdmin();

        LambdaQueryWrapper<DictItemPo> queryWrapper = new LambdaQueryWrapper<DictItemPo>()
                .eq(DictItemPo::getItemId, itemId)
                .eq(DictItemPo::getMctNo, SessionUtil.getDefaultMctNo());
        dictItemMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    public R update(DictItemUpdate update) {
        SessionUtil.assertAdmin();

        DictItemPo dictItemPo = DictItemPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, dictItemPo);

        LambdaQueryWrapper<DictItemPo> queryWrapper = new LambdaQueryWrapper<DictItemPo>()
                .eq(DictItemPo::getItemId, update.getItemId())
                .eq(DictItemPo::getMctNo, SessionUtil.getDefaultMctNo());
        dictItemMapper.update(dictItemPo, queryWrapper);
        return R.ok("修改成功");
    }

    public R<DictItemDetail> detail(Integer itemId) {
        return R.ok(dictItemMapper.detail(itemId));
    }

    public R<Page<DictItemBrief>> list(DictItemQuery query) {
        PageUtil.startPage(query);
        return R.page(dictItemMapper.list(query));
    }
}

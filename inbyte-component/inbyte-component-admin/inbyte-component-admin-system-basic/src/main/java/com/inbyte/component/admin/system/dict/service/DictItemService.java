package com.inbyte.component.admin.system.dict.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.dict.dao.InbyteDictItemMapper;
import com.inbyte.component.admin.system.dict.model.dict.item.*;
import com.inbyte.component.admin.system.user.SessionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典项服务
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Service
public class DictItemService {

    @Autowired
    private InbyteDictItemMapper inbyteDictItemMapper;

    public R insert(DictItemInsert insert) {
        SessionUtil.assertAdmin();

        LambdaQueryWrapper<DictItemPo> queryWrapper = new LambdaQueryWrapper<DictItemPo>()
                .eq(DictItemPo::getDictId, insert.getDictId())
                .eq(DictItemPo::getMctNo, SessionUtil.getMctNo());
        Long count = inbyteDictItemMapper.selectCount(queryWrapper);

        DictItemPo dictItemPo = DictItemPo.builder()
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .mctNo(SessionUtil.getMctNo())
                .build();
        BeanUtils.copyProperties(insert, dictItemPo);

        dictItemPo.setOrdinal(BigDecimal.valueOf(count));
        inbyteDictItemMapper.insert(dictItemPo);
        return R.ok("新增成功");
    }

    public R delete(Integer itemId) {
        SessionUtil.assertAdmin();

        LambdaQueryWrapper<DictItemPo> queryWrapper = new LambdaQueryWrapper<DictItemPo>()
                .eq(DictItemPo::getItemId, itemId)
                .eq(DictItemPo::getMctNo, SessionUtil.getMctNo());
        inbyteDictItemMapper.delete(queryWrapper);
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
                .eq(DictItemPo::getMctNo, SessionUtil.getMctNo());
        inbyteDictItemMapper.update(dictItemPo, queryWrapper);
        return R.ok("修改成功");
    }

    public R<DictItemDetail> detail(Integer itemId) {
        return R.ok(inbyteDictItemMapper.detail(itemId));
    }

    public R<Page<DictItemBrief>> list(DictItemQuery query) {
        PageUtil.startPage(query);
        return R.page(inbyteDictItemMapper.list(query));
    }
}

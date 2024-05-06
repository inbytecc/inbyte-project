package com.inbyte.component.admin.system.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.dict.model.dict.item.DictItemPo;
import com.inbyte.component.admin.system.dict.model.dict.item.DictItemQuery;
import com.inbyte.component.admin.system.dict.model.dict.item.DictItemBrief;
import com.inbyte.component.admin.system.dict.model.dict.item.DictItemDetail;
import java.util.List;

/**
 * 字典项
 *
 * 表名：  dict_item
 * @author chenjw
 * @date 2023-12-17 22:36:34
 */
public interface InbyteDictItemMapper extends BaseMapper<DictItemPo> {

    /**
     * 详情
     *
     * @param itemId
     * @return DictItemDetail
     **/
    DictItemDetail detail(Integer itemId);

    /**
     * 查询列表
     * @param query
     * @return List<DictItemBrief>
     **/
    List<DictItemBrief> list(DictItemQuery query);

}

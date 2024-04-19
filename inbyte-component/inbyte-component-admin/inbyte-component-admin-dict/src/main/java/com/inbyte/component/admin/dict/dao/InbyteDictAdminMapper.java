package com.inbyte.component.admin.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.dict.model.DictBrief;
import com.inbyte.component.admin.dict.model.DictDetail;
import com.inbyte.component.admin.dict.model.DictPo;
import com.inbyte.component.admin.dict.model.DictQuery;

import java.util.List;

/**
 * 字典
 *
 * 表名：  dict
 * @author chenjw
 * @date 2023-12-17 22:12:01
 */
public interface InbyteDictAdminMapper extends BaseMapper<DictPo> {

    /**
     * 详情
     *
     * @param dictId
     * @return DictDetail
     **/
    DictDetail detail(String dictId);

    /**
     * 查询列表
     * @param query
     * @return List<DictBrief>
     **/
    List<DictBrief> list(DictQuery query);

}

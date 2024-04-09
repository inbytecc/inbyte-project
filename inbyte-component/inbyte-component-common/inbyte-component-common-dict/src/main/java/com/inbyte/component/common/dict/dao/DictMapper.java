package com.inbyte.component.common.dict.dao;

import com.inbyte.component.common.dict.model.DictItemBrief;
import com.inbyte.commons.model.dto.Dict;

import java.util.List;

/**
 * 字典
 *
 * 表名：  dict
 * @author chenjw
 * @date 2023-12-17 22:12:01
 */
public interface DictMapper {

    /**
     * 查询列表
     * @param dictName
     * @return List<DictBrief>
     **/
    List<Dict> dictList(String dictName);

    /**
     * 查询列表
     * @param dictName
     * @return List<DictBrief>
     **/
    List<DictItemBrief> list(String dictName);
}

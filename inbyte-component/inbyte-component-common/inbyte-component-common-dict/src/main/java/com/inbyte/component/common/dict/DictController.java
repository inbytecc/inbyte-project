package com.inbyte.component.common.dict;

import com.inbyte.component.common.dict.model.DictItemBrief;
import com.inbyte.commons.model.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 字典
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 常规字典查询
     * <p>
     * 使用方法：https://d-app.pyrange.com/api/dict/【字典名称】</br>
     * 例如运动类型为sportType, 则查询地址为: https://d-app.pyrange.com/api/dict/sportType </br>
     * 订单状态： https://d-app.pyrange.com/api/dict/orderStatus </br>
     * 订单类型： https://d-app.pyrange.com/api/dict/orderType </br>
     * @param dictName 字典名称
     * @return
     */
    @GetMapping("{dictName}")
    public R<Map<String, String>> dict(@PathVariable String dictName) {
        return R.ok(dictService.getDict(dictName));
    }

    /**
     * 树结构字典
     *
     * <p>
     * 使用方法：https://d-app.pyrange.com/api/dict/【字典名称】</br>
     * 例如运动类型为sportType, 则查询地址为: https://d-app.pyrange.com/api/dict/sportType </br>
     * 订单状态： https://d-app.pyrange.com/api/dict/orderStatus </br>
     * 订单类型： https://d-app.pyrange.com/api/dict/orderType </br>
     * @param dictName 字典名称
     * @return
     */
    @GetMapping("{dictName}/tree")
    public R<List<DictItemBrief>> tree(@PathVariable String dictName) {
        return R.ok(dictService.getDictTree(dictName));
    }
}

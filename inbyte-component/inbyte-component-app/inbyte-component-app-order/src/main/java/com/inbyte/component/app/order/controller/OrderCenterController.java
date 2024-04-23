package com.inbyte.component.app.order.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.order.model.OrderCenterBrief;
import com.inbyte.component.app.order.model.OrderQuery;
import com.inbyte.component.app.order.service.OrderCenterService;
import com.inbyte.component.app.user.framework.aspect.Logged;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单中心
 *
 * ##########################################################################################################
 * # 特别说明
 * # 本设计特地弱化订单中心功能，它的作用仅仅只是小程序订单列表搜索，不增加任何其它财务、数据统计、数据冗余等逻辑
 * # 订单业务需要同步状态到中心
 * ##########################################################################################################
 * <p>
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年5月19日 下午3:19:10
 */
@RestController
@RequestMapping("order")
public class OrderCenterController {

    @Autowired
    private OrderCenterService orderCenterService;

    /**
     * 订单列表
     *
     * @param orderQuery
     * @return
     */
    @Logged
    @GetMapping
    public R<List<OrderCenterBrief>> list(@Valid @ModelAttribute OrderQuery orderQuery) {
        return R.ok(orderCenterService.list(orderQuery));
    }

    /**
     * 删除订单
     *
     * @param orderNo 订单编号
     * @return
     */
    @Logged
    @PostMapping("{orderNo}/delete")
    public R delete(@PathVariable String orderNo) {
        return orderCenterService.delete(orderNo);
    }

}

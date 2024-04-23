package com.inbyte.component.app.order.service;

/**
 * 订单服务工厂
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年05月06日
 */
public interface OrderServiceFactory {


    /**
     * 使用订单编号前2位规则获取订单通用服务
     *
     * @param orderNo
     * @return
     */
    OrderServiceCommonApi getServiceByOrderNo(String orderNo);

}

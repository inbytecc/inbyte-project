package com.inbyte.component.app.order.service.impl;

import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dict.OrderTypeEnum;
import com.inbyte.component.app.order.service.OrderServiceCommonApi;
import com.inbyte.component.app.order.service.OrderServiceFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单服务工厂
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年05月06日
 */
@Component
public class OrderServiceFactoryImpl implements OrderServiceFactory, InitializingBean, ApplicationContextAware {

    private static final Map<OrderTypeEnum, OrderServiceCommonApi> ORDER_SERVICES = new HashMap<>();
    private ApplicationContext applicationContext;

    /**
     * 通用订单操作init方法中注册服务关系
     */
    @Override
    public void afterPropertiesSet() {
        Map<String, OrderServiceCommonApi> beansOfType = applicationContext.getBeansOfType(OrderServiceCommonApi.class);
        for (Map.Entry<String, OrderServiceCommonApi> entry : beansOfType.entrySet()) {
            ORDER_SERVICES.put(entry.getValue().getOrderType(), entry.getValue());
        }
    }

    /**
     * 使用订单编号前3位规则获取订单通用服务
     *
     * @param orderNo
     * @return
     */
    public OrderServiceCommonApi getServiceByOrderNo(String orderNo) {
        OrderServiceCommonApi genericOrderService = ORDER_SERVICES.get(OrderTypeEnum.getByOrderNo(orderNo));
        if (genericOrderService == null) {
            throw InbyteException.error("没有对应的订单服务, 请联系技术客服处理");
        } else {
            return genericOrderService;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

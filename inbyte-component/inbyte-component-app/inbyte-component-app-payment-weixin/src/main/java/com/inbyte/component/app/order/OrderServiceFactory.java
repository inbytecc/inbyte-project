package com.inbyte.component.app.order;

import com.inbyte.commons.model.dict.OrderTypeEnum;
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
public class OrderServiceFactory implements InitializingBean, ApplicationContextAware {

    private static final Map<OrderTypeEnum, OrderServiceCommonApi> ORDER_SERVICES = new HashMap<>();
    private ApplicationContext applicationContext;

    /**
     * 通用订单操作init方法中注册服务关系
     */
    @Override
    public void afterPropertiesSet() {
//        ORDER_SERVICES.put(OrderTypeDict.Site_Order, applicationContext.getBean(OrderSiteService.class));
//        ORDER_SERVICES.put(OrderTypeEnum.Course_Order, applicationContext.getBean(OrderCourseService.class));
//        ORDER_SERVICES.put(OrderTypeDict.Venue_Ticket_Order, venueTicketOrderService);
//        ORDER_SERVICES.put(OrderTypeDict.Venue_Course_Order, venueCourseOrderService);
//        ORDER_SERVICES.put(OrderTypeDict.Venue_Vending_Machine_Order, vendingMachineOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Venue_QuickPay_Order, quickPayOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Activity_Order, activityOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.App_Wallet_Recharge_Order, appWalletOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Card_Balance_Recharge_Order, rechargeCardOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Match_Order, matchOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Card_Times_Recharge_Order, timesCardOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Card_Month_Recharge_Order, monthsCardOrderServiceRPC);
//        ORDER_SERVICES.put(OrderTypeDict.Coach_Course_Order, coachCourseOrderServiceRpc);
//        ORDER_SERVICES.put(OrderTypeDict.Venue_Site_Idle_Order, venueIdleOrderServiceRpc);
    }

    /**
     * 使用订单编号前2位规则获取订单通用服务
     *
     * @param orderNo
     * @return
     */
    public OrderServiceCommonApi getServiceByOrderNo(String orderNo) {
        OrderServiceCommonApi genericOrderService = ORDER_SERVICES.get(OrderTypeEnum.getByOrderNo(orderNo));
        if (genericOrderService == null) {
            throw new IllegalArgumentException(
                    "请在" + OrderServiceFactory.class.getSimpleName() +"的init方法中注册对应订单服务");
        } else {
            return genericOrderService;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

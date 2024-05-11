package com.inbyte.commons.model.dict;

/**
 * 订单类型
 * 杭州易思网络
 * @author chenjw
 * @date 2016年08月15日
 */
public enum OrderTypeEnum {

    CAMP_SITE("CMP", "营地订单", "15m"),
    TICKET("TKT", "门票订单", "15m"),

    CARD_VALUE("CDV", "储值卡订单", "15m"),
    CARD_TIMES("CDT", "计次卡订单", "15m"),
    CARD_PERIOD("CDP", "期间卡订单", "15m"),

    GOODS("GDS", "商品订单", "15m"),

    COUPON("CPN", "优惠券订单", "15m"),
    ;

    public final String code;
    public final String name;
    public final String timeoutExpress;

    OrderTypeEnum(String code, String name, String timeoutExpress) {
        this.code = code;
        this.name = name;
        this.timeoutExpress = timeoutExpress;
    }

    /**
     * 根据订单号取得订单类型枚举
     * @param orderNo
     * @return
     */
    public static OrderTypeEnum getByOrderNo(String orderNo) {
        return getByCode(orderNo.substring(0, 3));
    }

//    /**
//     * 根据code值取得订单类型枚举
//     * @param code
//     * @return
//     */
//    public static OrderTypeDict getByCode(String code) {
//        int intCode = Integer.parseInt(code);
//        return getByCode(intCode);
//    }

    /**
     * 根据code值取得订单类型枚举
     * @param code
     * @return
     */
    public static OrderTypeEnum getByCode(String code) {
        OrderTypeEnum[] values = OrderTypeEnum.values();
        for (OrderTypeEnum otc : values) {
            if (otc.code.equals(code)) {
                return otc;
            }
        }
        throw new IllegalArgumentException("订单类型" +code + "的枚举:" + OrderTypeEnum.class.getName() + "未定义");
    }
}

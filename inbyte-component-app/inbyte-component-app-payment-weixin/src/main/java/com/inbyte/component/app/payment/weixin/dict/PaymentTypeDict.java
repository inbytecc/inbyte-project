package com.inbyte.component.app.payment.weixin.dict;

/**
 * 付款平台类型
 * 杭州湃橙体育科技有限公司
 *
 * @author chenjw
 * @date 2016年08月15日
 */
public enum PaymentTypeDict {

    Cash_Pay(0, "现金"),
    WeiXin_Pay(10, "微信支付"),
    AliPay(20, "支付宝"),
    Other_Pay(99, "其它");


    public final int code;
    public final String name;

    PaymentTypeDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 是否需要与商家结算
     *
     * @param paymentType
     * @return
     */
    public static boolean checkable(Integer paymentType) {
        if (paymentType == null) {
            return false;
        }
//        return paymentType == AliPay.code ||
//               paymentType == WeiXin_Pay.code ||
//               paymentType == WeiXin_Service_Pay.code ||
////               paymentType == dxBao.code ||
//               paymentType == Dojoy_Wallet.code ||
//               paymentType == Dojoy_Bracelet_Pay.code;
        return paymentType == WeiXin_Pay.code;
    }

    public static boolean checkable(PaymentTypeDict paymentType) {
        if (paymentType == null) {
            return false;
        }
        return checkable(paymentType.code);
    }

//    /**
//     * 是否直接支付, 不需要再与微信支付宝东西钱包会员卡进行交互
//     * @param paymentType 支付类型
//     */
//    public static boolean directPaymentAble(Integer paymentType){
//        if (paymentType == null) {
//            return false;
//        }
//        return paymentType == Cash_Pay.code ||
//                paymentType == Voucher_Pay.code ||
//                paymentType == Cheque_Pay.code ||
//                paymentType == Bank_Card_Pay.code ||
//                paymentType == Other_Pay.code;
//    }

//    /**
//     * 动享支付方式, 支付后如果场馆支付时userID不确定, 则可设置支付时的用户id
//     */
//    public static boolean dxPaymentType(Integer paymentType){
//        if (paymentType == null) {
//            return false;
//        }
//        return paymentType == Balance_Card.code ||
//                paymentType == Dojoy_Wallet.code ||
//                paymentType == Dojoy_Bracelet_Pay.code;
//    }

}

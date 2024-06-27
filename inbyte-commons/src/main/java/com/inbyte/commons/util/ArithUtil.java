package com.inbyte.commons.util;

import com.inbyte.commons.exception.InbyteException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四则运算工具
 * 易思网络
 *
 * @author chenjw
 * @date 2016年06月30日
 */
public class ArithUtil {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * 将 null 转换为默认值
     *
     * @param value
     * @return
     */
    private static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private static BigDecimal nullToZero(Number value) {
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }

    public static Integer add(Integer addend, Integer summand) {
        return nullToZero(addend).intValue() + nullToZero(summand).intValue();
    }

    public static BigDecimal add(BigDecimal addend, BigDecimal summand) {
        return nullToZero(addend).add(nullToZero(summand)).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
        return nullToZero(minuend).subtract(nullToZero(subtrahend)).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal multiply(BigDecimal multiplicand, BigDecimal multiplier) {
        return nullToZero(multiplicand).multiply(nullToZero(multiplier)).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        if (nullToZero(divisor).equals(BigDecimal.ZERO)) {
            throw InbyteException.error("divisor can not be null or zero");
        }
        return nullToZero(dividend).divide(nullToZero(divisor), SCALE, ROUNDING_MODE);
    }

    public static double add(Double addend, Double summand) {
        return nullToZero(addend).add(nullToZero(summand)).setScale(SCALE, ROUNDING_MODE).doubleValue();
    }

//    public static double subtract(Double minuend, Double subtrahend) {
//        return nullToZero(minuend).subtract(nullToZero(subtrahend)).setScale(SCALE, ROUNDING_MODE).doubleValue();
//    }
//
//    public static double multiply(Double multiplicand, Integer multiplier) {
//        return nullToZero(multiplicand).multiply(nullToZero(multiplier)).setScale(SCALE, ROUNDING_MODE).doubleValue();
//    }
//
//    public static BigDecimal multiply(Integer multiplicand, Integer multiplier) {
//        return nullToZero(multiplicand).multiply(nullToZero(multiplier)).setScale(SCALE, ROUNDING_MODE);
//    }

//    public static double divide(Double dividend, Integer divisor) {
//        if (nullToZero(divisor).equals(BigDecimal.ZERO)) {
//            throw InbyteException.error("divisor can not be null or zero");
//        }
//        return nullToZero(dividend).divide(nullToZero(divisor), SCALE, ROUNDING_MODE).doubleValue();
//    }

    public static BigDecimal divide(Integer dividend, Integer divisor) {
        if (nullToZero(divisor).equals(BigDecimal.ZERO)) {
            throw InbyteException.error("divisor can not be null or zero");
        }
        return nullToZero(dividend).divide(nullToZero(divisor), SCALE, ROUNDING_MODE);
    }

    public static BigDecimal divide(Long dividend, Integer divisor) {
        if (nullToZero(divisor).equals(BigDecimal.ZERO)) {
            throw InbyteException.error("divisor can not be null or zero");
        }
        return nullToZero(dividend).divide(nullToZero(divisor), SCALE, ROUNDING_MODE);
    }

    public static BigDecimal add(Number addend, Number summand) {
        return nullToZero(addend).add(nullToZero(summand)).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal subtract(Number minuend, Number subtrahend) {
        return nullToZero(minuend).subtract(nullToZero(subtrahend)).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal multiply(Number multiplicand, Number multiplier) {
        return nullToZero(multiplicand).multiply(nullToZero(multiplier)).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal divide(Number dividend, Number divisor) {
        if (nullToZero(divisor).equals(BigDecimal.ZERO)) {
            throw InbyteException.error("divisor can not be null or zero");
        }
        return nullToZero(dividend).divide(nullToZero(divisor), SCALE, ROUNDING_MODE);
    }


    /**
     * 判断第一个BigDecimal是否大于第二个BigDecimal。
     *
     * @param num1 要比较的第一个BigDecimal
     * @param num2 要比较的第二个BigDecimal
     * @return 如果num1大于num2，则返回true，否则返回false
     */
    public static boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }

    /**
     * 判断第一个BigDecimal是否小于第二个BigDecimal。
     *
     * @param num1 要比较的第一个BigDecimal
     * @param num2 要比较的第二个BigDecimal
     * @return 如果num1小于num2，则返回true，否则返回false
     */
    public static boolean isLessThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0;
    }

    /**
     * 判断两个BigDecimal是否相等。
     *
     * @param num1 要比较的第一个BigDecimal
     * @param num2 要比较的第二个BigDecimal
     * @return 如果num1等于num2，则返回true，否则返回false
     */
    public static boolean isEqual(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }

    /**
     * 判断第一个BigDecimal是否大于或等于第二个BigDecimal。
     *
     * @param num1 要比较的第一个BigDecimal
     * @param num2 要比较的第二个BigDecimal
     * @return 如果num1大于或等于num2，则返回true，否则返回false
     */
    public static boolean isGreaterThanOrEqualTo(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) >= 0;
    }

    /**
     * 判断第一个BigDecimal是否小于或等于第二个BigDecimal。
     *
     * @param num1 要比较的第一个BigDecimal
     * @param num2 要比较的第二个BigDecimal
     * @return 如果num1小于或等于num2，则返回true，否则返回false
     */
    public static boolean isLessThanOrEqualTo(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) <= 0;
    }
}

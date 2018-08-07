package com.bottle.moviesapp.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * Created by mengbaobao on 2018/4/20.
 * 文本实用类
 */

public class TextUtil {

    /**
     * 判断文本不为空
     *
     * @param txt
     * @return
     */
    public static boolean isValidate(String txt) {
        return txt != null && !"".equals(txt.trim());
    }

    /**
     * 判断集合不为空
     *
     * @param list
     * @return
     */
    public static boolean isValidate(Collection<?> list) {
        return list != null && list.size() > 0;
    }

    /**
     * 判断集合不为空
     *
     * @param map
     * @return
     */
    public static boolean isValidate(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    public static int parseInt(String number) {
        return parseInt(number, 0);
    }

    public static int parseInt(String number, int defaultValue) {
        int value = defaultValue;
        try {
            value = Integer.parseInt(number);
        } catch (Exception ignore) {
        }
        return value;
    }

    public static double parseDouble(String number) {
        return parseDouble(number, 0);
    }

    public static double parseDouble(String number, double defaultValue) {
        double value = defaultValue;
        try {
            value = Double.parseDouble(number);
        } catch (Exception ignore) {
        }
        return value;
    }

    public static long parseLong(String number) {
        return parseLong(number, 0);
    }

    public static long parseLong(String number, long defaultValue) {
        long value = defaultValue;
        try {
            value = Long.parseLong(number);
        } catch (Exception ignore) {
        }
        return value;
    }

    /**
     * 保留小数点后2位
     * 使用{@link BigDecimal#ROUND_DOWN} 模式
     */
    public static String keepDecimal2(double number, String defaultValue) {
        return keepDecimal(number, defaultValue, 2);
    }

    /**
     * 保留小数点后decimalPlace位
     * 使用{@link BigDecimal#ROUND_DOWN} 模式
     */
    public static String keepDecimal(double number, String defaultValue, int decimalPlace) {
        return keepDecimal(number, defaultValue, decimalPlace, BigDecimal.ROUND_DOWN);
    }

    /**
     * 根据roundedMode保留小数点后decimalPlace位
     * roundedMode 请查看{@link BigDecimal}
     */
    public static String keepDecimal(double number, String defaultValue, int decimalPlace, int roundedMode) {
        String value = defaultValue;
        try {
            BigDecimal b = new BigDecimal(number);
            value = b.setScale(decimalPlace, roundedMode).toString();
        } catch (Exception ignore) {
        }
        return value;
    }


    /**
     * 保留小数点后2位
     * 使用{@link BigDecimal#ROUND_DOWN} 模式
     */
    public static String keepDecimal2(String number, String defaultValue) {
        return keepDecimal(number, defaultValue, 2);
    }

    /**
     * 保留小数点后decimalPlace位
     * 使用{@link BigDecimal#ROUND_DOWN} 模式
     */
    public static String keepDecimal(String number, String defaultValue, int decimalPlace) {
        return keepDecimal(number, defaultValue, decimalPlace, BigDecimal.ROUND_DOWN);
    }

    /**
     * 根据roundedMode保留小数点后decimalPlace位
     * roundedMode 请查看{@link BigDecimal}
     */
    public static String keepDecimal(String number, String defaultValue, int decimalPlace, int roundedMode) {
        String value = defaultValue;
        try {
            BigDecimal b = new BigDecimal(number);
            value = b.setScale(decimalPlace, roundedMode).toString();
        } catch (Exception ignore) {
        }
        return value;
    }

    public static String upperCaseFirstLetter(String str){
        char[] strChar=str.toCharArray();
        strChar[0]-=32;
        return String.valueOf(strChar);
    }
}

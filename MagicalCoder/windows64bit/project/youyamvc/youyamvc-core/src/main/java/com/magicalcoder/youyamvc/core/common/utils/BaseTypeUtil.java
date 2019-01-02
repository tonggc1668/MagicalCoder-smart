package com.magicalcoder.youyamvc.core.common.utils;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BaseTypeUtil {

    public static Long toLong(Object value){
        return Long.valueOf(value.toString());
    }

    public static Integer toInteger(Object value){
        return Integer.valueOf(value.toString());
    }

    public static String toString(Object value){
        return value.toString();
    }

    public static BigDecimal toBigDecimal(Object value){
        return new BigDecimal(value.toString());
    }

    public static Float toFloat(Object value){
        return Float.valueOf(value.toString());
    }

    public static Double toDouble(Object value){
        return Double.valueOf(value.toString());
    }
}

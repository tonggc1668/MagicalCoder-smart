package com.magicalcoder.youyamvc.core.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by www.magicalcoder.com on 14-5-21.
 * 799374340@qq.com
 */
public class NumberUtils {

    private final static String format = "0.##";
    public static String bigDecimalFormat(BigDecimal decimal,String format){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(decimal);
    }
    public static String bigDecimalFormat(BigDecimal decimal){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(decimal);
    }
    public static String bigDecimalFormat(String num,String format){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(num);
    }
    public static String bigDecimalFormat(String num){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(num);
    }
}

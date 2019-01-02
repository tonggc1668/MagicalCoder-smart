package com.magicalcoder.youyamvc.core.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www.magicalcoder.com on 14-12-30.
 * 799374340@qq.com
 */
public class IpAutoCreator {
//    private static long min = 3238002688L;//11000001000000000000000000000000 193.0.0.0
    private static long min = 3238029652L;//11000001000000000000000000000000 193.0.0.0

    private final static long max = 4294967295L;//11111111111111111111111111111111 二进制转化的十进制 255.255.255.255
    public static void main(String[] args) {
//        ip(100000);
//        ip(100000);

//        System.out.println("======");
//        ip(255);
//        System.out.println("======");
//        ip(255);
        while (true){
            String ip = randomIp();
            System.out.println(ip);
        }
    }
    public static List<String> ip(int count){
        if(min>max) min=3238002688L;//恢复最小值
        List<String> ips = new ArrayList<String>();
        int i = 1;
        for(;min<max;){
            long nowIp = min++;
            String binary = Long.toBinaryString(nowIp);
            //分成4段
            StringBuffer sb = new StringBuffer();
            String a = binary.replaceAll("(\\d{8})(\\d{8})(\\d{8})(\\d{8})","$1");
            String b = binary.replaceAll("(\\d{8})(\\d{8})(\\d{8})(\\d{8})","$2");
            String c = binary.replaceAll("(\\d{8})(\\d{8})(\\d{8})(\\d{8})","$3");
            String d = binary.replaceAll("(\\d{8})(\\d{8})(\\d{8})(\\d{8})","$4");
            sb.append(Integer.valueOf(a,2)).append(".").
                    append(Integer.valueOf(b,2)).
                    append(".").
                    append(Integer.valueOf(c,2)).
                    append(".").
                    append(Integer.valueOf(d,2));
            ips.add(sb.toString());
            if(i++>=count){
                break;
            }
        }
//        Log4jUtils.getLog(IpAutoCreator.class).info(DateFormatUtils.getStringDate(new Date(),"yyyy-MM-dd HH:mm:ss")+"  ip min = "+min);
//        System.out.println(DateFormatUtils.getStringDate(new Date(),"yyyy-MM-dd HH:mm:ss")+"  ip min = "+min);
        return ips;
    }

    public static String randomIp(){
       List<String> ips = ip(1);
       return ips.get(0);
    }
}

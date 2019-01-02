package com.magicalcoder.youyamvc.core.common.utils;

/**
 * Created by www.magicalcoder.com on 2015/4/30.
 * 799374340@qq.com
 */
public class PageUtil {

    /*当前页第一条排名*/
    public static int buildSortNum(Integer pgIdx,Integer maxLimit){
        if(pgIdx==null)pgIdx=0;
        if(maxLimit==null)maxLimit=0;
        int begin = (pgIdx-1)*maxLimit;
        return begin;
    }
}

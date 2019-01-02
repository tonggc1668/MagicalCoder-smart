package com.magicalcoder.youyamvc.core.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by www.magicalcoder.com on 2015/5/26.
 * 799374340@qq.com
 */
public class MapUtil {
    public static Map<String,Object> buildMap(String key,Object value ,Object... args){
        Map<String,Object> query = new HashMap<String, Object>();
        query.put(key, value);
        if(args!=null && args.length>0){
            if(args.length%2 != 0 ){
                throw new RuntimeException("key value要成对");
            }
            for(int i=0;i<args.length;){
                query.put((String)args[i],args[i+1]);
                i=i+2;
            }
        }
        return query;
    }
    public static Map<String,String> buildStringMap(String key,String value ,String... args){
        Map<String,String> query = new HashMap<String, String>();
        query.put(key, value);
        if(args!=null && args.length>0){
            if(args.length%2 != 0 ){
                throw new RuntimeException("key value要成对");
            }
            for(int i=0;i<args.length;){
                query.put((String)args[i],args[i+1]);
                i=i+2;
            }
        }
        return query;
    }

    public static <T> Map<T,T> buildTMap(T key,T value ,T... args){
        Map<T,T> query = new HashMap<T, T>();
        query.put(key, value);
        if(args!=null && args.length>0){
            if(args.length%2 != 0 ){
                throw new RuntimeException("key value要成对");
            }
            for(int i=0;i<args.length;){
                query.put((T)args[i],args[i+1]);
                i=i+2;
            }
        }
        return query;
    }
}

package com.magicalcoder.youyamvc.app.userweb.util;

import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;

/**
* Created by hdy.
* 799374340@qq.com
 * 手机验证码
*/
public class ValidCodeUtil {
    
    private static final String validCodePrefix = "validPrefix";//注册是手机验证码缓存前缀 key

    /**
     * 验证码的缓存key
     * @param useType 使用类型 register update_mobile
     * @param sessionId
     * @return
     */
    public static String validCodeCacheKey(String useType,String sessionId){
        return useType + validCodePrefix + sessionId;
    }

    /**
     * 存储验证码
     * @param useType 使用类型 register update_mobile
     * @param sessionId
     * @param code
     */
    public static void setValidCodeToCache(String useType,String sessionId,String code){
        CacheUtil.resetCache(validCodeCacheKey(useType,sessionId),10*60,code);
    }

    /**
     * 从缓存获取验证码
     * @param sessionId
     * @return
     */
    public static String getValidCodeFromCache(String useType,String sessionId){
        return CacheUtil.get(validCodeCacheKey(useType,sessionId));
    }

}

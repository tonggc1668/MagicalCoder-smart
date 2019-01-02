package com.magicalcoder.youyamvc.app.userweb.util;

import com.magicalcoder.youyamvc.app.userweb.dto.UserWebDto;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.utils.SysPropertiesUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
* Created by hdy.
* 799374340@qq.com
*/
public class UserWebUtil {
    
    private static final String cookieKey = "webLoginSession";//登录后设置的cookie key
    private static final String ssoCookieKey = "ssoWebLoginSession";//单点登录后设置的cookie key
    public static String getLoginCookieKey(){
        return cookieKey;
    }
    /**
     * 用户登录后的缓存key
     * @param sessionId
     * @return
     */
    public static String userCacheKey(String sessionId){
        return cookieKey + sessionId;
    }

    /**
     * 单点登录
     * @param userId
     * @return
     */
    public static String userSsoCacheKey(Long userId){
        return ssoCookieKey + userId;
    }
    public static void setUserDtoToRequest(HttpServletRequest request,UserWebDto userDto){
        request.setAttribute(cookieKey,userDto);
    }

    /**
     * 获取登录用户id
     * @param request
     * @return
     */
    public static Long userId(HttpServletRequest request){
        UserWebDto userWebDto = getUserDtoFromRequest(request);
        if(userWebDto!=null){
            return userWebDto.getId();
        }
        return null;
    }

    public static UserWebDto getUserDtoFromRequest(HttpServletRequest request){
        return (UserWebDto)request.getAttribute(cookieKey);
    }

    public static UserWebDto getUserDtoFromCache(HttpServletRequest request){
        String sessionId = getLoginCookieValue(request);
        if(StringUtils.isBlank(sessionId)){
            return null;
        }
        try {
            String cacheKey = userCacheKey(sessionId);
            Object obj = CacheUtil.get(cacheKey);
            return (UserWebDto)obj;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getLoginCookieValue(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                if(cookieKey.equals(cookie.getName().trim())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    //排序部分防sql注入安全过滤
    public static String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        if(descAsc!=null && !"".equals(descAsc.trim())){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                String orderBySqlFieldStr = ",id,user_name,regist_time,";
                if(orderBySqlFieldStr.contains("" + orderBySqlField+"")){//精确匹配可排序字段
                    orderBy = orderBySqlField+" "+descAsc;
                }
            }
        }
        return orderBy;
    }

    //排序部分防sql注入安全过滤
    public static String filterOrderBy(String orderByStr){
        StringBuilder sb = new StringBuilder();
        if(orderByStr!=null && !"".equals(orderByStr.trim())){
            orderByStr = orderByStr.toLowerCase().trim();
            String[] arr = orderByStr.split(",");
            for(String sqlOrderField:arr){
                String orderBySqlFieldStr = ",id,user_name,regist_time,";
                String sqlField = sqlOrderField.replaceAll("\\s+asc|\\s+desc","").trim();
                if(!"".equals(sqlField)&&orderBySqlFieldStr.contains(sqlField)){//精确匹配可排序字段
                    sb.append(sqlOrderField).append(",");
                }
            }
        }
        if("".equals(sb.toString())){
            return null;
        }
        if(sb.toString().endsWith(",")){
            return sb.substring(0,sb.length()-1);
        }
        return null;
    }
    //是否开启单点登录
    public static boolean ssoOpen(){
        return "true".equals(SysPropertiesUtil.getProperty("SIMPLE_SINGLE_LOGIN").trim());
    }
}

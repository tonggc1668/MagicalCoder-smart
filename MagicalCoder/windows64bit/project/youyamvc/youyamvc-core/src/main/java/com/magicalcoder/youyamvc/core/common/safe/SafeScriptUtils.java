package com.magicalcoder.youyamvc.core.common.safe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by www.magicalcoder.com on 14-12-1.
 * 799374340@qq.com
 */
public class SafeScriptUtils {

    private Map<String, String> getParams(HttpServletRequest req) {
        Map<String, String> result = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Map<String, String[]> reqMap = req.getParameterMap();
        for (Map.Entry<String, String[]> e : reqMap.entrySet()) {
            result.put(e.getKey(), e.getValue()[0]);
        }
        return result;
    }
    public String checkXssSqlInject(HttpServletRequest request,
                         HttpServletResponse response)throws Exception{
        String requestUrl = request.getRequestURL().toString();
        String path = request.getContextPath();
        String port = 80==request.getServerPort() ? "" : ":"+request.getServerPort();
        String adminBasePath = request.getScheme()+"://"+request.getServerName()+port+path+"/admin";
        if(requestUrl.startsWith(adminBasePath)){
            return null;
        }
        StringBuffer queryStr = new StringBuffer();
        Map<String,String> reqMap = getParams(request);
        Set<String> keys = reqMap.keySet();
        for(String key:keys){
            String value = reqMap.get(key);
//            if(!"pagingInfo".equals(key)){//放过pagingInfo
                queryStr.append(key).append("=").append(value).append("|||");//放过&符号
//            }
        }
        //Xss攻击
        String url = request.getRequestURL().append("?").append(queryStr).toString();

//        String method = request.getMethod();
//        if("GET".equals(method.toUpperCase())){//暂时不考虑post
            XssHtmlFilter xss = new XssHtmlFilter();
            if(xss.isContainXss(url) || SqlInjectFilter.isContainSqlInject(url)){
                System.out.println(url+"包含Xss或者SqlInject");
                return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            }
//        }

        return null;
    }
}

package com.magicalcoder.youyamvc.core.http.utils;

import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.http.dto.ReqHeader;
import com.magicalcoder.youyamvc.core.http.dto.ReqReturn;

import java.util.HashMap;

public class InitUtil {

    public static ReqHeader initReqHeader(ReqHeader reqHeader){
        if(reqHeader==null){
            reqHeader=new ReqHeader();
        }
        if(reqHeader.getTimeOut()==null){
            reqHeader.setTimeOut(5000);
        }
        if(StringUtils.isBlank(reqHeader.getEncode())){
            reqHeader.setEncode("UTF-8");
        }
        if(reqHeader.getHeaderMap()==null){
            reqHeader.setHeaderMap(new HashMap<String, String>());
        }
        return reqHeader;
    }

    public static boolean success200(ReqReturn reqReturn){
        return "200".equals(reqReturn.getStatus());
    }
}

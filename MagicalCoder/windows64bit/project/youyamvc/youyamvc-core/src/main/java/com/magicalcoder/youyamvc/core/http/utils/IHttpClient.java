package com.magicalcoder.youyamvc.core.http.utils;


import com.magicalcoder.youyamvc.core.http.dto.ReqHeader;
import com.magicalcoder.youyamvc.core.http.dto.ReqReturn;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.util.Map;

/**
 * Created by hzhedongyu on 2015/10/14.
 */
public interface IHttpClient {

    ReqReturn requestByJson(String reqUrl,String jsonReq,ContentType contentType);
    ReqReturn requestByJson(String reqUrl, String jsonReq,ContentType contentType, ReqHeader reqHeader);
    ReqReturn request(String reqUrl);
    ReqReturn request(String reqUrl, Map<String, String[]> reqMap);
    ReqReturn request(String reqUrl, Map<String, String[]> reqMap, ReqHeader reqHeader);
    ReqReturn request(String reqUrl, Map<String, String[]> reqMap, Map<String, File> fileMap, ReqHeader reqHeader);
}

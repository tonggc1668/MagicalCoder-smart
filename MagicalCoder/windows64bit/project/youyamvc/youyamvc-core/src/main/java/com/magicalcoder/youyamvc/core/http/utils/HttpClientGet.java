package com.magicalcoder.youyamvc.core.http.utils;

import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.utils.log.Log4jUtils;
import com.magicalcoder.youyamvc.core.http.dto.ReqHeader;
import com.magicalcoder.youyamvc.core.http.dto.ReqReturn;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzhedongyu on 2015/10/14.
 */
public class HttpClientGet implements IHttpClient {
    static Logger logger = Log4jUtils.getLog(HttpClientGet.class);

    private static final HttpClientGet instance = new HttpClientGet();
    private HttpClientGet(){

    }


    public static void main(String[] args) {
        ReqHeader header = new ReqHeader();
        Map<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Cookie", "session=wvjLVkrH8EPUPkzZcOBpC8FJ7JVqWF72uL-owE8i6p9cyL4A;");
        header.setHeaderMap(headerMap);
        ReqReturn reqReturn = HttpClientPost.get().request("http://123.58.175.191:8078/api/mobile/index.php?version=163&module=onlineusernum", null, header);
        System.out.println(reqReturn.getBody());
    }

    public static HttpClientGet get(){
        return instance;
    }


    @Override
    public ReqReturn requestByJson(String reqUrl, String jsonReq, ContentType contentType) {
        return null;
    }

    @Override
    public ReqReturn requestByJson(String reqUrl, String jsonReq, ContentType contentType, ReqHeader reqHeader) {
        return null;
    }

    @Override
    public ReqReturn request(String reqUrl) {
        return request(reqUrl, null, null,null);
    }

    @Override
    public ReqReturn request(String reqUrl, Map<String, String[]> reqMap) {
        return request(reqUrl, reqMap, null,null);
    }

    @Override
    public ReqReturn request(String reqUrl, Map<String, String[]> reqMap, ReqHeader reqHeader) {
        return request(reqUrl,reqMap,null,reqHeader);
    }

    @Override
    public ReqReturn request(String reqUrl, Map<String, String[]> reqMap, Map<String, File> fileMap, ReqHeader reqHeader) {
        String status="";
        String returnResponse = "";
        Header[] allHeader = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuffer dataURL = new StringBuffer("?");
        if(reqMap!=null && !reqMap.isEmpty()){
            Set<String> keys = reqMap.keySet();
            for(String key:keys){
                String[] value = reqMap.get(key);
                for (String v:value){
                    dataURL.append(key).append("=").append(v).append("&");
                }
            }
        }
        reqHeader = InitUtil.initReqHeader(reqHeader);
        HttpGet get = null;
        String url = reqUrl + dataURL.substring(0, dataURL.length() - 1);
        try {
            get = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(reqHeader.getTimeOut()).setConnectTimeout(reqHeader.getTimeOut()).build();//设置请求和传输超时时间
            get.setConfig(requestConfig);
            Map<String,String> headerMap = reqHeader.getHeaderMap();
            if(headerMap!=null && !headerMap.isEmpty()){
                Set<String> keys = headerMap.keySet();
                for(String key:keys){
                    get.addHeader(key, headerMap.get(key));
                }
            }

            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            returnResponse = EntityUtils.toString(entity);
            status = response.getStatusLine().getStatusCode()+"";
            allHeader = response.getAllHeaders();
            EntityUtils.consume(entity);
        } catch (Exception e) {
            if(StringUtils.isBlank(status)){
                status="0000";
            }
            returnResponse = e.getMessage();
            System.out.println(url +" " + e.getMessage());
            logger.error(url);
            logger.error(e.getMessage());
        } finally {
            if(get!=null){
                get.releaseConnection();
                get.abort();
            }
        }
        ReqReturn reqReturn = new ReqReturn();
        reqReturn.setStatus(status);
        if("200".equals(status)){
            reqReturn.setSuccess(true);
        }else {
            reqReturn.setSuccess(false);
        }
        reqReturn.setBody(returnResponse);
        reqReturn.setHeaders(allHeader);
        return reqReturn;
    }
}

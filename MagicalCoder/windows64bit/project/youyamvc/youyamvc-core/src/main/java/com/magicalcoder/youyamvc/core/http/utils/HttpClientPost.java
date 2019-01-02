package com.magicalcoder.youyamvc.core.http.utils;

import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.utils.log.Log4jUtils;
import com.magicalcoder.youyamvc.core.http.dto.ReqHeader;
import com.magicalcoder.youyamvc.core.http.dto.ReqReturn;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzhedongyu on 2015/10/14.
 */
public class HttpClientPost implements IHttpClient {
    static Logger logger = Log4jUtils.getLog(HttpClientPost.class);

    private static final HttpClientPost instance = new HttpClientPost();
    private HttpClientPost(){

    }

    public static HttpClientPost get(){
        return instance;
    }

    @Override
    public ReqReturn requestByJson(String reqUrl, String jsonReq,ContentType contentType) {
        return requestByJson(reqUrl,jsonReq,contentType,null);
    }
    @Override
    public ReqReturn requestByJson(String reqUrl, String jsonReq,ContentType contentType, ReqHeader reqHeader) {
        String status="";
        String returnResponse = "";
        Header[] allHeader = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        reqHeader = InitUtil.initReqHeader(reqHeader);
        HttpPost post = null;
        try {
            post = new HttpPost(reqUrl);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(reqHeader.getTimeOut()).setConnectTimeout(reqHeader.getTimeOut()).build();//设置请求和传输超时时间
            post.setConfig(requestConfig);
            Map<String,String> headerMap = reqHeader.getHeaderMap();
            if(headerMap!=null && !headerMap.isEmpty()){
                Set<String> keys = headerMap.keySet();
                for(String key:keys){
                    post.addHeader(key, headerMap.get(key));
                }
            }

            HttpEntity entity;
            if(contentType!=null){
                entity = new StringEntity(jsonReq,contentType);
            }else {
                entity = new StringEntity(jsonReq);
            }
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            status = response.getStatusLine().getStatusCode()+"";
            allHeader = response.getAllHeaders();
            if (responseEntity != null) {
                returnResponse = EntityUtils.toString(responseEntity);
                EntityUtils.consume(responseEntity);
            }

        } catch (Exception e) {
            if(StringUtils.isBlank(status)){
                status="0000";
            }
            System.out.println(reqUrl+" "+e.getMessage());
            logger.error(reqUrl);
            logger.error(JSON.toJSONString(e));
            returnResponse = e.getMessage();

        } finally {
            if(post!=null){
                post.releaseConnection();
                post.abort();
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

    @Override
    public ReqReturn request(String reqUrl) {
        return request(reqUrl,null,null,null);
    }

    @Override
    public ReqReturn request(String reqUrl, Map<String, String[]> reqMap) {
        return request(reqUrl,reqMap,null,null);
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
        reqHeader = InitUtil.initReqHeader(reqHeader);
        HttpPost post = null;
        try {
            post = new HttpPost(reqUrl);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(reqHeader.getTimeOut()).setConnectTimeout(reqHeader.getTimeOut()).build();//设置请求和传输超时时间
            post.setConfig(requestConfig);
            Map<String,String> headerMap = reqHeader.getHeaderMap();
            if(headerMap!=null && !headerMap.isEmpty()){
                Set<String> keys = headerMap.keySet();
                for(String key:keys){
                    post.addHeader(key, headerMap.get(key));
                }
            }

            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            if(reqMap!=null && !reqMap.isEmpty()){
                Set<String> keys = reqMap.keySet();
                for(String key:keys){
                    String[] value = reqMap.get(key);
                    for (String v:value){
                        pairs.add(new BasicNameValuePair(key, v));
                    }
                }
            }

            HttpEntity entity = new UrlEncodedFormEntity(pairs, reqHeader.getEncode());
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            status = response.getStatusLine().getStatusCode()+"";
            allHeader = response.getAllHeaders();
            if (responseEntity != null) {
                returnResponse = EntityUtils.toString(responseEntity);
                EntityUtils.consume(responseEntity);
            }

        } catch (Exception e) {
            if(StringUtils.isBlank(status)){
                status="0000";
            }
            System.out.println(reqUrl+" "+e.getMessage());
            logger.error(reqUrl);
            logger.error(JSON.toJSONString(e));
            returnResponse = e.getMessage();

        } finally {
            if(post!=null){
                post.releaseConnection();
                post.abort();
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

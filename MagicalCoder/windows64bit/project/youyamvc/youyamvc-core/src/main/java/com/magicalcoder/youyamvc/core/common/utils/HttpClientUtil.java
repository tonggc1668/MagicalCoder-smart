package com.magicalcoder.youyamvc.core.common.utils;

import com.magicalcoder.youyamvc.core.common.utils.log.Log4jUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    static Logger logger = Log4jUtils.getLog(HttpClientUtil.class);
	public static String[] doPost(String basePath, List<BasicNameValuePair> request) {
        String status="";
        String returnResponse = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(basePath);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);

            HttpEntity entity = new UrlEncodedFormEntity(request, "UTF-8");
            httpPost.addHeader("User-Agent", "http://www.magicalcoder.com/ contact: su_yan#AT#corp.magicalcoder.com");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            status = response.getStatusLine().getStatusCode()+"";
            if (responseEntity != null) {
                returnResponse = EntityUtils.toString(responseEntity);
                EntityUtils.consume(responseEntity);
            }
        } catch (ClientProtocolException e) {
            status="ClientProtocolException";
            e.printStackTrace();
        } catch (IllegalStateException e) {
            status="IllegalStateException";
            e.printStackTrace();
        } catch (IOException e) {
            status="IOException";
            e.printStackTrace();
        } catch (Exception e) {
            status="Exception";
            e.printStackTrace();
            logger.error(basePath);
        } finally {
            if(httpPost!=null){
                httpPost.releaseConnection();
                httpPost.abort();
            }
        }
        return new String[]{status,returnResponse};
    }

    public static String[] doGet(String httpUrl,List<BasicNameValuePair> request) {
        String status="";
        String returnResponse = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuffer dataURL = new StringBuffer("?");
        if(request!=null && request.size()>=0){
            for (BasicNameValuePair basicNameValuePair : request) {
                dataURL.append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue()).append("&");
            }
        }
        HttpGet get = null;
        try {
            get = new HttpGet(httpUrl + dataURL.substring(0, dataURL.length() - 1));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
            get.setConfig(requestConfig);
    //        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0");
            get.addHeader("User-Agent", "http://www.magicalcoder.com/ contact: su_yan#AT#corp.magicalcoder.com");
            get.addHeader("Content-type", "application/json;charset=UTF-8");
            get.addHeader("Accept", "application/json");
            //创建HttpGet实例
            HttpResponse response;

            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            returnResponse = EntityUtils.toString(entity);
            status = response.getStatusLine().getStatusCode()+"";
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            status="ClientProtocolException";
            e.printStackTrace();
        } catch (IOException e) {
            status="IOException";
            e.printStackTrace();
        }catch (Exception e) {
            status="Exception";
            e.printStackTrace();
        } finally {
            if(get!=null){
                get.releaseConnection();
                get.abort();
            }

        }
        return new String[]{status,returnResponse};
    }

    public static String[] doGet(String httpUrl,List<BasicNameValuePair> request,Map<String,Object> paramMap) {
        String status="";
        String returnResponse = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuffer dataURL = new StringBuffer("?");
        if(request!=null && request.size()>=0){
            for (BasicNameValuePair basicNameValuePair : request) {
                dataURL.append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue()).append("&");
            }
        }
        HttpGet get = null;
        try {
            if(paramMap==null){
                paramMap=new HashMap<String, Object>();
            }
            Integer timeOut = (Integer) paramMap.get("timeOut");
            if(timeOut==null){
                timeOut = 5000;
            }
            get = new HttpGet(httpUrl + dataURL.substring(0, dataURL.length() - 1));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
            get.setConfig(requestConfig);
            String charset = (String)paramMap.get("charset");
            if(StringUtils.isBlank(charset)){
                charset = "UTF-8";
            }
            get.addHeader("User-Agent", "http://www.magicalcoder.com/ contact: su_yan#AT#corp.magicalcoder.com");
            get.addHeader("Content-type", "application/json;charset="+charset);
            get.addHeader("Accept", "application/json");
            String userIp = (String)paramMap.get("userIp");
            if(StringUtils.isNotBlank(userIp)){
                get.addHeader("X-Forwarded-For", userIp);
                get.addHeader("Proxy-Client-IP", userIp);
                get.addHeader("WL-Proxy-Client-IP", userIp);
                get.addHeader("HTTP_CLIENT_IP", userIp);
                get.addHeader("API-RemoteIP", userIp);
                get.addHeader("CLIENT-IP", userIp);
            }
            //创建HttpGet实例
            HttpResponse response;

            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            returnResponse = EntityUtils.toString(entity);
            status = response.getStatusLine().getStatusCode()+"";
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            status="ClientProtocolException";
            e.printStackTrace();
        } catch (IOException e) {
            status="IOException";
            e.printStackTrace();
        }catch (Exception e) {
            status="Exception";
            e.printStackTrace();
        } finally {
            if(get!=null){
                get.releaseConnection();
                get.abort();
            }
        }
        return new String[]{status,returnResponse};
    }

    public static boolean success200(String status){
        return "200".equals(status);
    }
}

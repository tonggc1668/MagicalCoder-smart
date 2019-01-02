package com.magicalcoder.youyamvc.core.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
    * 协议 https
 * 利用HttpClient进行post请求的工具类 忽略证书
 */
public class HttpsClientUtils {

    public static String[] doPostNoCert(String url,Map<String,String> paramMap,String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        String status = "";
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = paramMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
            status = response.getStatusLine().getStatusCode() + "";
        }catch(Exception ex){
            status = ex.getMessage();
            ex.printStackTrace();
        }
        return new String[]{status, result};
    }

    public static boolean success200(String status) {
        return "200".equals(status);
    }

    /**
     *  https 无证书
     * @param basePath
     * @param request
     * @param unicodeType
     * @return
     */
    public static String[] doPostNoCert(String basePath, List<BasicNameValuePair> request,String unicodeType) {
        String status = "";
        String returnResponse = "";
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(basePath);
            HttpClient httpClient = new SSLClient();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
            httpPost.setConfig(requestConfig);
            UrlEncodedFormEntity e = new UrlEncodedFormEntity(request,unicodeType);
            httpPost.setEntity(e);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            status = response.getStatusLine().getStatusCode() + "";
            if(responseEntity != null) {
                returnResponse = EntityUtils.toString(responseEntity);
                EntityUtils.consume(responseEntity);
            }
        } catch (ClientProtocolException var16) {
            status = "ClientProtocolException";
            var16.printStackTrace();
        } catch (IllegalStateException var17) {
            status = "IllegalStateException";
            var17.printStackTrace();
        } catch (IOException var18) {
            status = "IOException";
            var18.printStackTrace();
        } catch (Exception var19) {
            status = "Exception";
            var19.printStackTrace();
        } finally {
            if(httpPost!=null){
                httpPost.releaseConnection();
                httpPost.abort();
            }
        }

        return new String[]{status, returnResponse};
    }

    /**
     *  https 无证书
     * @param request
     * @return
     */
    public static String[] doGetNoCert(String httpUrl, List<BasicNameValuePair> request) {
        String status = "";
        String returnResponse = "";
        StringBuffer dataURL = new StringBuffer("?");
        if(request!=null && request.size()>=0){
            for (BasicNameValuePair basicNameValuePair : request) {
                dataURL.append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue()).append("&");
            }
        }
        HttpGet get = null;
        try {
            get = new HttpGet(httpUrl + dataURL.substring(0, dataURL.length() - 1));
            HttpClient httpClient = new SSLClient();
//            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60);
//            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
            get.setConfig(requestConfig);

            HttpResponse response = httpClient.execute(get);
            HttpEntity responseEntity = response.getEntity();
            status = response.getStatusLine().getStatusCode() + "";
            if(responseEntity != null) {
                returnResponse = EntityUtils.toString(responseEntity);
                EntityUtils.consume(responseEntity);
            }
        } catch (ClientProtocolException var16) {
            status = "ClientProtocolException";
            var16.printStackTrace();
        } catch (IllegalStateException var17) {
            status = "IllegalStateException";
            var17.printStackTrace();
        } catch (IOException var18) {
            status = "IOException";
            var18.printStackTrace();
        } catch (Exception var19) {
            status = "Exception";
            var19.printStackTrace();
        } finally {
            if(get!=null){
                get.releaseConnection();
                get.abort();
            }

        }

        return new String[]{status, returnResponse};
    }

    public static void main(String[] args) {
        String url = "http://www.google.com";
        String[] arr ;
        arr = doGetNoCert(url,null);
        System.out.println(arr[1]);
    }
}

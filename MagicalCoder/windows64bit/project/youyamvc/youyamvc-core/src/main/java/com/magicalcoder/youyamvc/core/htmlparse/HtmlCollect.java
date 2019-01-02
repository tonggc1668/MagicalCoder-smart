package com.magicalcoder.youyamvc.core.htmlparse;

import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by www.magicalcoder.com on 14-7-21.
 * 799374340@qq.com
 */
public class HtmlCollect {

    public static String getCollectAndWrite(String httpUrl,String storeTargetFileUrl,Map<String,Object> args){
        Integer timeOut = (Integer)args.get("timeOut");
        String status = "";
        InputStream returnResponse;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(httpUrl);
        //针对一般服务器Circular redirect 可以加上.setCircularRedirectsAllowed(true)
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeOut).setConnectTimeout(timeOut).setCircularRedirectsAllowed(true).build();//设置请求和传输超时时间
        get.setConfig(requestConfig);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0");
        get.addHeader("Content-type", "text/html;charset=UTF-8");
        //创建HttpGet实例
        HttpResponse response;
        try {
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            returnResponse = entity.getContent();
            FileHelper.fastWriteFile(storeTargetFileUrl, returnResponse);
            status = response.getStatusLine().getStatusCode()+"";
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            System.out.println(httpUrl);
            status="ClientProtocolException";
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(httpUrl);
            status="IOException";
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println(httpUrl);
            status="Exception";
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            get.abort();
        }
        return status;
    }

    public static String getCollectAndWite(String httpUrl,String storeTargetFileUrl){
        String status = "";
        InputStream returnResponse;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(httpUrl);
        //针对一般服务器Circular redirect 可以加上.setCircularRedirectsAllowed(true)
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000).setConnectTimeout(5000).setCircularRedirectsAllowed(true).build();//设置请求和传输超时时间
        get.setConfig(requestConfig);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0");
        get.addHeader("Content-type", "text/html;charset=UTF-8");
        //创建HttpGet实例
        HttpResponse response;
        try {
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            returnResponse = entity.getContent();
            FileHelper.fastWriteFile(storeTargetFileUrl, returnResponse);
            status = response.getStatusLine().getStatusCode()+"";
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            System.out.println(httpUrl);
            status="ClientProtocolException";
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(httpUrl);
            status="IOException";
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println(httpUrl);
            status="Exception";
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            get.abort();
        }
        return status;
    }
    public static String[] getCollect(String httpUrl,List<BasicNameValuePair> request){
        String status="";
        String returnResponse = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuffer dataURL = new StringBuffer("?");
        if(request!=null && request.size()>=0){
            for (BasicNameValuePair basicNameValuePair : request) {
                dataURL.append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue()).append("&");
            }
        }
        HttpGet get = new HttpGet(httpUrl + dataURL.substring(0, dataURL.length() - 1));
        //针对一般服务器Circular redirect 可以加上.setCircularRedirectsAllowed(true)
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000).setConnectTimeout(5000).setCircularRedirectsAllowed(true).build();//设置请求和传输超时时间
        get.setConfig(requestConfig);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0");
        get.addHeader("Content-type", "text/html;charset=UTF-8");
        //创建HttpGet实例
        HttpResponse response;
        try {
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
            get.releaseConnection();
            get.abort();
        }
        return new String[]{status,returnResponse};
    }
    public static boolean success200(String status){
        return "200".equals(status);
    }
}

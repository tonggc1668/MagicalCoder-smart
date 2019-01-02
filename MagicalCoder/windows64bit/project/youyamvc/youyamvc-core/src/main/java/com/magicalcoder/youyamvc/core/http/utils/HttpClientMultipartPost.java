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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * multipart post 有文件上传的表单
 * Created by hzhedongyu on 2015/10/14.
 */
public class HttpClientMultipartPost implements IHttpClient {
    static Logger logger = Log4jUtils.getLog(HttpClientMultipartPost.class);

    private static final HttpClientMultipartPost instance = new HttpClientMultipartPost();
    private HttpClientMultipartPost(){

    }

    public static HttpClientMultipartPost get(){
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    /*    Map<String,File> fileMap = new HashMap<String, File>();
        fileMap.put("myfiles",new File("D:\\myfile\\signcheck-0.0.7.jar"));
        ReqReturn reqReturn =HttpClientMultipartPost.get().request("http://localhost/tianyu/center/fileupload/business", null, fileMap, null);
        System.out.println(reqReturn.getBody());*/
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
        return null;
    }

    @Override
    public ReqReturn request(String reqUrl, Map<String, String[]> reqMap) {
        return null;
    }

    @Override
    public ReqReturn request(String reqUrl, Map<String, String[]> reqMap, ReqHeader reqHeader) {
        return null;
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

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.STRICT);
            //普通参数
            if(reqMap!=null && !reqMap.isEmpty()){
                Set<String> keys = reqMap.keySet();
                for(String key:keys){
                    String[] value = reqMap.get(key);
                    for (String v:value){
                        builder.addTextBody(key, v, ContentType.TEXT_PLAIN.withCharset(reqHeader.getEncode()));
                    }
                }
            }
            //文件参数
            if(fileMap!=null && !fileMap.isEmpty()){
                Set<String> keys = fileMap.keySet();
                for(String key:keys){
                    File file = fileMap.get(key);
                    builder.addPart(key,new FileBody(file));
                }
            }
            HttpEntity multipartEntity = builder.build();
            post.setEntity(multipartEntity);

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

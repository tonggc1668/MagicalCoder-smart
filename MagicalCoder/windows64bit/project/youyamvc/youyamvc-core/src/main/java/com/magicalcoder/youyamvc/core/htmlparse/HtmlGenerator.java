package com.magicalcoder.youyamvc.core.htmlparse;

import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 静态页面引擎技术
 * @author
 *
 */
public class HtmlGenerator {
    /** 根据模版及参数产生静态页面 */
    public static boolean createUTF8HtmlPage(String url,String htmlFileName){
        String statusCode="";
        String returnResponse;
        //创建一个HttpClient实例充当模拟浏览器
        HttpClient httpClient = HttpClientBuilder.create().build();
        //创建GET方法的实例
        HttpGet get = new HttpGet(url);
        //设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
        get.addHeader("Content-type", "text/html;charset=UTF-8");
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(12000).setConnectTimeout(12000).build();//设置请求和传输超时时间
        get.setConfig(requestConfig);
        HttpResponse response;
        try{
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            returnResponse = EntityUtils.toString(entity);
            statusCode = response.getStatusLine().getStatusCode()+"";
            if(!"200".equals(statusCode)){//非正常200
                System.out.println("数据长度：" + returnResponse.length() + "返回状态码：" + response.getStatusLine());
            }else{
                //将解析结果写入指定的静态HTML文件中，实现静态HTML生成
                FileHelper.fastWriteFileUTF8(htmlFileName,returnResponse);
            }
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            statusCode="ClientProtocolException";
            e.printStackTrace();
        } catch (IOException e) {
            statusCode="IOException";
            e.printStackTrace();
        }catch (Exception e) {
            statusCode="Exception";
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            get.abort();
        }
        return "200".equals(statusCode);
    }

    //将解析结果写入指定的静态HTML文件中
//    private synchronized void writeHtml(String htmlFileName,String content) throws Exception{
//        fw = new BufferedWriter(new FileWriter(htmlFileName));
//        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(htmlFileName),"UTF-8");
//        fw.write(page);
//        if(fw!=null)fw.close();
//    }

    //将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
//    private String formatPage(String page){
//        page = page.replaceAll("\\.\\./\\.\\./\\.\\./", webappname+"/");
//        page = page.replaceAll("\\.\\./\\.\\./", webappname+"/");
//        page = page.replaceAll("\\.\\./", webappname+"/");
//        return page;
//    }

    //测试方法
    public static void main(String[] args){
        String domain = "D:\\herofire\\html\\";
        HtmlGenerator h = new HtmlGenerator();
        h.createUTF8HtmlPage("http://localhost:8080/hero/murky",domain+"murky.html");
        System.out.println("静态页面已经生成");
    }

}

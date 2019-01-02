package com.magicalcoder.youyamvc.core.htmlparse;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by www.magicalcoder.com on 14-8-28.
 * 799374340@qq.com
 * 支持js加载处理的html采集
 */
public class HunitHtmlPager {
    public static HtmlPage getPage(String url) throws IOException {
        final WebClient webClient = HunitWebClientFactory.getWebClient();
        java.net.URL URL = new URL(url);
        return webClient.getPage(URL);
    }

    public static void main(String[] args) throws IOException {

    }

    private static String js (){
        return "document.getElementById('pageHeight').html";
//        return "";
    }
}

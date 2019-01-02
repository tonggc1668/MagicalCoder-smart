package com.magicalcoder.youyamvc.core.htmlparse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created by www.magicalcoder.com on 14-8-28.
 * 799374340@qq.com
 */
public class HunitWebClientFactory {
    private static WebClient webClient;
    public static WebClient getWebClient(){
        if(webClient!=null){
            return webClient;
        }
        // 模拟一个浏览器
        webClient = new WebClient(BrowserVersion.getDefault());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.waitForBackgroundJavaScript(10*1000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setJavaScriptEnabled(true);
        return webClient;
    }
    public static void closeAllWindows(){
        webClient.closeAllWindows();
    }
}

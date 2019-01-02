package com.magicalcoder.youyamvc.core.common.safe;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * http://jsoup.org/apidocs/org/jsoup/safety/Whitelist.html 参考
 * Created by www.magicalcoder.com on 2015/6/16.
 * 799374340@qq.com
 */
public class JsoupXssFilter {
    //白名单
    private final static Whitelist writeFilter = Whitelist.relaxed();

    static {
        writeFilter.addTags("embed", "object", "param", "span", "div");
        writeFilter.addAttributes(":all", "style", "class", "id", "name");
        writeFilter.addAttributes("object", "width", "height", "classid", "codebase");
        writeFilter.addAttributes("param", "name", "value");
        writeFilter.addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type", "pluginspage");
    }

    public static String cleanXssToSafe(String  html){
        return Jsoup.clean(html, writeFilter);
    }

    public static void main(String[] args) {
        System.out.println(cleanXssToSafe("<table><a href='www.baidu.com'>d</a></table>"));
    }
}

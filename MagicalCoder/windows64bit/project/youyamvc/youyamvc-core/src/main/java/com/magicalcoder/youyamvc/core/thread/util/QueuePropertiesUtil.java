package com.magicalcoder.youyamvc.core.thread.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by www.magicalcoder.com on 14-6-10.
 * 799374340@qq.com
 */
public class QueuePropertiesUtil {
    private static InputStream ios;
    private static Properties p;
    public static String getProperty(final String key){
        if(ios==null){
            ios = QueuePropertiesUtil.class.getResourceAsStream("/queue.properties");
        }
        if(p==null){
            p = new Properties();
            try {
                p.load(ios);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p.getProperty(key);
    }
}

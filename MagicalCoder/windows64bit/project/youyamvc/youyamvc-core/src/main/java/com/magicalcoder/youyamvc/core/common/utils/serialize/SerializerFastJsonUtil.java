package com.magicalcoder.youyamvc.core.common.utils.serialize;
/*
	by 何栋宇
	2013-1-28
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.util.List;


public class SerializerFastJsonUtil {
	private static SerializerFastJsonUtil instance = new SerializerFastJsonUtil();

	private SerializerFastJsonUtil(){}
	
	public static SerializerFastJsonUtil get(){
		return instance;
	}
	//json转化java对象
	public <T> T readJsonObject(String json,Class<T> beanClazz){
		if(StringUtils.isBlank(json)){
			return null;
		}
		return JSON.parseObject(json, beanClazz);
	}
	//json转化java对象
	public <T> List<T> readJsonList(String json,Class<T> beanClazz){
		if(StringUtils.isBlank(json)){
			return null;
		}
		return JSON.parseArray(json, beanClazz);
	}

    /**
     * 对象转字符
     * @param o
     * @param config 日期会自动格式化
     * @param features 如果为空 则null参数也会输出
     * @return
     */
   public String toJSONString(Object o,SerializeConfig config,SerializerFeature... features){
       if(config==null){
           config = new SerializeConfig();
           config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
       }
       if(features==null){
           features = new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue};
       }
        return JSON.toJSONString(o,config,features);
   }
    /**
     * 自动格式化json字符串
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String formatJson(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c+"\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public static String replaceSystemDot(String val){
        return val.replace(",","，").replace("\"","\'").replace("\r","").replace("\n","");
    }

}


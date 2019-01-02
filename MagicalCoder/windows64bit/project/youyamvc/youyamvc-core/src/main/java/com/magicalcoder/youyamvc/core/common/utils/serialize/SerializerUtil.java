package com.magicalcoder.youyamvc.core.common.utils.serialize;
/*
	by 何栋宇
	2013-1-28
 */

import com.alibaba.fastjson.JSON;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class SerializerUtil {
	private static SerializerUtil instance = new SerializerUtil();

	private SerializerUtil(){}
	
	public static SerializerUtil get(){
		return instance;
	}
	public <T> T readMapJsonObject( Class<T> beanClass,Map<String,String> reqMap) {
		String jsonString = JSON.toJSONString(reqMap);
		if(jsonString==null || "".equals(jsonString)){
			return null;
		}
		return (T)readJsonObject(jsonString, beanClass, null);
	}
	public Object readJsonObject(String jsonString, Class<?> beanClass) {
		return readJsonObject(jsonString, beanClass, null);
	}

	public List<Object> readJsonList(String jsonString, Class<?> pojoClz){
		//设定日期转换格式  
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd HH","yyyy-MM-dd" }));
		  
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClz);
			list.add(pojoValue);

		}
		return list;
	}
	// ----------private ------
	private Object readJsonObject(String jsonString, Class<?> beanClass,Map<String, Class<?>> classMap) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass( beanClass );
		if(classMap != null)
			jsonConfig.setClassMap( classMap );
		return JSONSerializer.toJava(JSONSerializer.toJSON(jsonString), jsonConfig);
	}
}


package com.magicalcoder.youyamvc.app.dict.listener;

import com.magicalcoder.youyamvc.app.dict.service.DictProxyService;
import com.magicalcoder.youyamvc.app.model.Dict;
import com.magicalcoder.youyamvc.app.model.dict.Dictionary;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.util.List;


public class DictionaryListener implements ApplicationListener<ContextRefreshedEvent> {
	@Resource
	private DictProxyService dictProxyService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("数据库字典初始化...");
		try{
			List<Dict> allDict = this.dictProxyService.getAllDict();
			if(ListUtils.isNotBlank(allDict)){
				for(Dict dict : allDict){
					Dictionary.autoInit(dict);
				}
			}
			System.out.println("数据库字典初始化完成^ ^");
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("数据库连接失败，请检查src/main/resources/jdbc.properties");
		}

		try {
			CacheUtil.get("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

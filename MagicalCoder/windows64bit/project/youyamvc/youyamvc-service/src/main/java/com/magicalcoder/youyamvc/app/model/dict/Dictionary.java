package com.magicalcoder.youyamvc.app.model.dict;

import com.magicalcoder.youyamvc.app.dto.SysConfig;
import com.magicalcoder.youyamvc.app.model.Dict;
import com.magicalcoder.youyamvc.core.common.utils.serialize.SerializerFastJsonUtil;

public class Dictionary {
    //系统配置map
    public static SysConfig sysConfig = new SysConfig();

	public static void flush(){
        sysConfig = new SysConfig();

	}
	public static void autoInit(Dict dict){
		int dictType = dict.getDictType();
		String dictValue = dict.getDictValue();
        String dictKey = dict.getDictKey();
		switch(dictType){
            case DictConstant.DICT_TYPE_SYS_CONFIG:
                sysConfig = SerializerFastJsonUtil.get().readJsonObject(dictValue, SysConfig.class);
                break;
            default: break;
		}
	}
}

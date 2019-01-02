package com.magicalcoder.youyamvc.core.common.utils.copy;

import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Copyer {
    private   static String builtGetMethod(String javaZd){
        return "get"+javaZd.substring(0,1).toUpperCase()+javaZd.substring(1);
    }
    private    static String builtSetMethod(String javaZd){
        return "set"+javaZd.substring(0,1).toUpperCase()+javaZd.substring(1);
    }

    /**
     * 为空直接不拷贝
     * @param cnField
     * @param sourceObj
     * @param targetObj
     * @param zero 为0是否拷贝
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void setValue(Field cnField,Object sourceObj,Object targetObj,boolean zero) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String cnName = cnField.getName();
        Class<?> type = cnField.getType();
        String typeName = type.getName();
        String getMethodName =  builtGetMethod(cnName);
        String setMethodName =  builtSetMethod(cnName);
        Method cnMethod = sourceObj.getClass().getMethod(getMethodName);
        Method enFieldMethod = targetObj.getClass().getMethod(getMethodName);
        if(enFieldMethod==null) return;
        Object v = cnMethod.invoke(sourceObj);
        if(v==null) return;
        if("java.lang.String".equals(typeName)){
            String value = (String)v;
            if(StringUtils.isNotBlank(value)){
                Method enMethod = targetObj.getClass().getMethod(setMethodName, String.class);
                enMethod.invoke(targetObj,value);
            }
        }else if("java.math.BigDecimal".equals(typeName)){
            BigDecimal value = (BigDecimal)v;
            if(zero||  value.compareTo(BigDecimal.ZERO)!=0){
                Method enMethod = targetObj.getClass().getMethod(setMethodName, BigDecimal.class);
                enMethod.invoke(targetObj,value);
            }
        }else if("java.lang.Integer".equals(typeName)){
            Integer value = (Integer)v;
            if( zero||    value.intValue()!=0){
                Method enMethod = targetObj.getClass().getMethod(setMethodName, Integer.class);
                enMethod.invoke(targetObj,value);
            }
        }else if("java.lang.Float".equals(typeName)){
            Float value = (Float)v;
            if(zero||  value.floatValue()!=0){
                Method enMethod = targetObj.getClass().getMethod(setMethodName, Float.class);
                enMethod.invoke(targetObj,value);
            }
        }else if("java.lang.Long".equals(typeName)){
            Long value = (Long)v;
            if( zero ||value.longValue()!=0){
                Method enMethod = targetObj.getClass().getMethod(setMethodName, Long.class);
                enMethod.invoke(targetObj,value);
            }
        }else if("java.util.Date".equals(typeName)){
            Date value = (Date)v;
            Method enMethod = targetObj.getClass().getMethod(setMethodName, Date.class);
            enMethod.invoke(targetObj,value);
        }
    }

    /** 拷贝基础对象 0拷贝 null不拷贝
     * @param from
     * @param to
     * @return
     */
    public static Object reflectCopyZeroNotNull(Object from, Object to){
        try{
            Field[] cnFields = from.getClass().getDeclaredFields();
            for(Field cnField : cnFields){
                setValue(cnField,from,to,true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return to;
    }
    /** 拷贝基础对象 0不拷贝 null不拷贝
     * @param from
     * @param to
     * @return
     */
    public static Object reflectCopyNotZeroNotNull(Object from, Object to){
        try{
            Field[] cnFields = from.getClass().getDeclaredFields();
            for(Field cnField : cnFields){
                setValue(cnField,from,to,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return to;
    }
	/** 拷贝基础对象
	 * @param from
	 * @param to
	 * @return
	 */
    @Deprecated
    public static Object copy(Object from, Object to){
		OgnlCopyer.copy(from,to,false,false);
		return to;
	}
	
	/** 拷贝list
	 * @param entitys  源数据
	 * @param toClazz 返回的数据类型
	 * @return
	 */
	public static <D,E>  List<D> copy(List<E> entitys,Class toClazz){
		if(entitys == null || entitys.size()<=0){
			return null;
		}
		List<D> ls = new ArrayList<D>();
		D d = null;
		try {
			for(E entity:entitys){
				d = (D) toClazz.newInstance();
				copy(entity,d);
				ls.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
}

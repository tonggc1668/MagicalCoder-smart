package com.magicalcoder.youyamvc.core.common.utils;

import com.magicalcoder.youyamvc.core.common.dto.InputSelectShowDto;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by www.magicalcoder.com on 2015/5/22.
 * 799374340@qq.com
 * version 2016-03-23
 */
public class ProjectUtil {
    public static Map<String,Object> buildMap(String key, Object value , Object... args){
        return MapUtil.buildMap(key, value, args);
    }

    //验证是否合法字符串 逗号分隔 数字中间
    public static boolean validIds(String ids){
        String reg = "[\\d,]+";
        return ids.matches(reg);
    }

    public static String reflectShowValue(String clazzFields, Object clazz){
        if(StringUtils.isNotBlank(clazzFields)){
            String[] attr = clazzFields.split(",");
            StringBuffer sb = new StringBuffer();
            Field fields[]=clazz.getClass().getDeclaredFields();//获得对象所有属性
            Field field=null;
            try {

                for (int i = 0; i < fields.length; i++) {
                    field=fields[i];
                    field.setAccessible(true);//修改访问权限
                    for (int j = 0; j < attr.length; j++) {
                        if (attr[j].equals(field.getName())) {
                            sb.append(field.get(clazz)).append("-");
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return StringUtils.deleteLastChar(sb.toString());
        }
        return null;
    }
    public static Object reflectValue(String clazzField, Object clazz){
        if(StringUtils.isNotBlank(clazzField)){
            Field fields[]=clazz.getClass().getDeclaredFields();//获得对象所有属性
            Field field=null;
            try {

                for (int i = 0; i < fields.length; i++) {
                    field=fields[i];
                    field.setAccessible(true);//修改访问权限
                    if (clazzField.equals(field.getName())) {
                        return field.get(clazz);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isNum(String str){
        return str.matches("[0-9\\.]+");
    }

    public static Map<String, Object> getParams(HttpServletRequest req) {
        Map<String, Object> result = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Map<String, String[]> reqMap = req.getParameterMap();
        for (Map.Entry<String, String[]> e : reqMap.entrySet()) {
            result.put(e.getKey(), e.getValue()[0]);
        }
        return result;
    }

    //异常处理
    public static String buildExceptionMsg(String message){
        String msg = null;
        //找重复唯一约束
        String reg = "Duplicate entry '(.*?)' for key";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()){
            msg = "请保证("+matcher.group(1)+")的唯一性";
            break;
        }
        //找不为空约束
        if(StringUtils.isBlank(msg)){
            return message;
        }else {
            return msg;
        }
    }
    //排序部分防sql注入安全过滤
    public static String filterOrderBy(String orderBySqlFieldStr,String orderBySqlField,String descAsc){
        String orderBy = null;
        if(descAsc!=null && !"".equals(descAsc.trim())){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                if(orderBySqlFieldStr.contains("" + orderBySqlField+"")){//精确匹配可排序字段
                    orderBy = orderBySqlField+" "+descAsc;
                }
            }
        }
        return orderBy;
    }

    //排序部分防sql注入安全过滤
    public static String filterOrderBy(String orderBySqlFieldStr,String orderByStr){
        StringBuilder sb = new StringBuilder();
        if(orderByStr!=null && !"".equals(orderByStr.trim())){
            orderByStr = orderByStr.toLowerCase().trim();
            String[] arr = orderByStr.split(",");
            for(String sqlOrderField:arr){
                String sqlField = sqlOrderField.replaceAll("\\s+asc|\\s+desc","").trim();
                if(!"".equals(sqlField)&&orderBySqlFieldStr.contains(sqlField)){//精确匹配可排序字段
                    sb.append(sqlOrderField).append(",");
                }
            }
        }
        if("".equals(sb.toString())){
            return null;
        }
        if(sb.toString().endsWith(",")){
            return sb.substring(0,sb.length()-1);
        }
        return null;
    }

    public static  <T> List<InputSelectShowDto> showList(List<T> list,String selectValue,String foreignJavaField){
        if(ListUtils.isNotBlank(list)){
            List<InputSelectShowDto> showList = new ArrayList<InputSelectShowDto>();
            showList.add(new InputSelectShowDto("全部",null));
            for(T entity:list){
                String showValue = ProjectUtil.reflectShowValue(selectValue,entity);
                Object hiddenId = ProjectUtil.reflectValue(foreignJavaField,entity);
                InputSelectShowDto dto = new InputSelectShowDto(showValue,hiddenId);
                showList.add(dto);
            }
            return showList;
        }
        return new ArrayList<InputSelectShowDto>();
    }
}

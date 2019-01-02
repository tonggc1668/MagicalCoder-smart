package com.magicalcoder.youyamvc.core.common.safe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by www.magicalcoder.com on 14-12-1.
 * 799374340@qq.com
 */
public class SqlInjectFilter {
    private static String SQL_RESTRICT_RULL= "(concat|and(%20|\\s)+|or(%20|\\s)+|sleep|drop(%20|\\s)+|insert(%20|\\s)+|select(%20|\\s)+|delete(%20|\\s)+|update(%20|\\s)+|\\*+|truncate(%20|\\s)+|;)";
    private static String SQL_RULL = "(concat|sleep|drop(%20|\\s)+|insert(%20|\\s)+|select(%20|\\s)+|delete(%20|\\s)+|update(%20|\\s)+|\\*+|truncate(%20|\\s)+|;)";
    public static boolean isContainSqlInject(String url){
        String rule = null;
        if(url.contains("pagingInfo")){
            rule = SQL_RULL;
        }else{
            rule = SQL_RESTRICT_RULL;
        }
        Pattern p = Pattern.compile(rule,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        if(m.find()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
    }
}

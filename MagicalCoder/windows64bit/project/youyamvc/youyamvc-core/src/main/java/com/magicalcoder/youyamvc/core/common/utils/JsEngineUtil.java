package com.magicalcoder.youyamvc.core.common.utils;

import com.magicalcoder.youyamvc.core.common.exception.BusinessException;
import com.magicalcoder.youyamvc.core.common.utils.log.Log4jUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 执行js引擎
 * Created by www.magicalcoder.com on 14-5-5.
 * 799374340@qq.com
 */
public class JsEngineUtil {
    /**
     *
     * @param function 被执行的方法
     * @param scriptCode js代码
     * @param param 入参
     * @return
     * @throws javax.script.ScriptException
     * @throws NoSuchMethodException
     */
    public static Object excute(String scriptCode,String function,Object... param) {
        // 创建一个 ScriptEngineManager
        ScriptEngineManager m = new ScriptEngineManager();
        // 得到javascript引擎
        ScriptEngine engine = m.getEngineByName("js");
        // 绑定引擎
        engine.put("engine", engine);
        Invocable inv = (Invocable) engine;
        try {
            engine.eval(scriptCode);
            return inv.invokeFunction(function, param);
        } catch (ScriptException e) {
            Log4jUtils.getLog(JsEngineUtil.class).error("js调用异常:"+param[0]);
            throw new BusinessException("SYS-0001","js调用异常:"+e);
        } catch (NoSuchMethodException e) {
            throw new BusinessException("SYS-0002","js调用异常:未找到此方法"+e);
        }
    }

    /**
     *
     * @param otherJsCode 如果不为空，将和远程返回的js拼接一起参与运算
     * @param jsRemoteUrl 远程调用的js地址
     * @param function 调用方法
     * @param param 入参
     * @return
     */
    public static Object excuteRemote(String otherJsCode,String jsRemoteUrl,String function,Object... param){
        String[] result = HttpClientUtil.doGet(jsRemoteUrl,null);
        if(HttpClientUtil.success200(result[0])){
            String scriptCode = result[1];
            // 创建一个 ScriptEngineManager
            ScriptEngineManager m = new ScriptEngineManager();
            // 得到javascript引擎
            ScriptEngine engine = m.getEngineByName("js");
            // 绑定引擎
            engine.put("engine", engine);
            Invocable inv = (Invocable) engine;
            try {
                if(StringUtils.isNotBlank(otherJsCode)){
                    engine.eval(otherJsCode+scriptCode);
                }else{
                    engine.eval(scriptCode);
                }
                return inv.invokeFunction(function, param);
            } catch (ScriptException e) {
                throw new BusinessException("SYS-0001","js调用异常:"+e);
            } catch (NoSuchMethodException e) {
                throw new BusinessException("SYS-0002","js调用异常:未找到此方法"+e);
            }
        }else{
            throw new BusinessException("SYS-0003",jsRemoteUrl+" 请求失败！");
        }
    }
}

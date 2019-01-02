package com.magicalcoder.youyamvc.core.spring.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.spring.PagingController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Date;

/**
 * 后台用户必须登录 并用于分页的controller
 * @author Administrator
 *
 */
public abstract class AdminLoginPagingController extends PagingController{
    static final SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat, SerializerFeature. WriteMapNullValue};
    static final SerializeConfig config = new SerializeConfig();
    static {
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    public void toJson(HttpServletResponse response, AjaxData ajaxData){
        try {
            byte[] bytes;
            bytes = JSON.toJSONString(ajaxData, config, features).getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

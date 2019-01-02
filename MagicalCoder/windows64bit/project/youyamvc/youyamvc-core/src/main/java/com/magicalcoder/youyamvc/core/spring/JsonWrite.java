package com.magicalcoder.youyamvc.core.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.dto.JsonData;
import com.magicalcoder.youyamvc.core.common.utils.ImgeUtil;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Date;

/**
 * Created by www.magicalcoder.com on 2015/6/23.
 * 799374340@qq.com
 */
public class JsonWrite {
    static final SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue};
    static final SerializerFeature[] simpleFeatures = new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat};
    static final SerializeConfig config = new SerializeConfig();
    static {
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response){
        toJsonData(response,new JsonData.Builder(null).code(0).build());
    }
    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,Object info){
        toJsonData(response,new JsonData.Builder(info).code(0).build());
    }
    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,Object info,boolean writeNull){
        toJsonData(response,new JsonData.Builder(info).code(0).writeNull(writeNull).build());
    }
    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,String callback){
        toJsonData(response,new JsonData.Builder(null).code(0).jsoup(callback).build());
    }
    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,String callback,String encode){
        toJsonData(response,new JsonData.Builder(null).code(0).jsoup(callback).encode(encode).build());
    }
    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,String callback,Object info){
        toJsonData(response,new JsonData.Builder(info).code(0).jsoup(callback).build());
    }
    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,String callback,String encode,Object info){
        toJsonData(response,new JsonData.Builder(info).code(0).jsoup(callback).encode(encode).build());
    }
    /**
     * 错误情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebFailureJson(HttpServletResponse response,String message){
        toJsonData(response,new JsonData.Builder(null).code(-1).message(message).build());
    }
    /**
     * 错误情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebFailureJson(HttpServletResponse response,String callback,String message){
        toJsonData(response,new JsonData.Builder(null).code(-1).jsoup(callback).message(message).build());
    }
    /**
     * 错误情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebFailureJson(HttpServletResponse response,String callback,String encode,String message){
        toJsonData(response,new JsonData.Builder(null).code(-1).jsoup(callback).message(message).encode(encode).build());
    }
    /**
     * 所有web版使用此方法
     * @param response
     * @param jsonData
     */
    /**
     * 所有admin使用此方法
     * @param response
     * @param ajaxData
     */
    public void toJson(HttpServletResponse response, AjaxData ajaxData){
        try {
            byte[] bytes;
            if(StringUtils.isBlank(ajaxData.getJsonp())){
                bytes = JSON.toJSONString(ajaxData,config,features).getBytes("UTF-8");
            }else{
                bytes = (ajaxData.getJsonp()+"("+JSON.toJSONString(ajaxData,config,features)+")").getBytes("UTF-8");
            }
            response.setCharacterEncoding("UTF-8");
            /*使用 text/html 则页面就需要 使用JSON.parse(data) 来处理 这种方法所有浏览器都支持 下面的
            * ie不支持啊*/
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 自定义类型输出json
     * @param response
     * @param ajaxData
     */
    public void toSimpleJson(HttpServletResponse response, Object ajaxData){
        try {
            byte[] bytes;
            bytes = JSON.toJSONString(ajaxData, config, new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat}).getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 所有web版使用此方法
     * @param response
     * @param jsonData
     */
    public void toJsonData(HttpServletResponse response, JsonData jsonData){
        String encode = "UTF-8";
        if(jsonData.getEncode()!=null){
            encode = jsonData.getEncode();
        }
        try {
            byte[] bytes;
            if(StringUtils.isBlank(jsonData.getJsonp())){
                if(jsonData.isWriteNull()){
                    bytes = JSON.toJSONString(jsonData, config, features).getBytes(encode);
                }else {
                    bytes = JSON.toJSONString(jsonData, config, simpleFeatures).getBytes(encode);
                }
            }else{
                if(jsonData.isWriteNull()){
                    bytes = (jsonData.getJsonp()+"("+JSON.toJSONString(jsonData,config,features)+")").getBytes(encode);
                }else {
                    bytes = (jsonData.getJsonp()+"("+JSON.toJSONString(jsonData,config,simpleFeatures)+")").getBytes(encode);
                }
            }
            response.setCharacterEncoding(encode);
            response.setContentType("text/json;charset="+encode);
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片流输出
     * @param response
     * @param imgUrl 图片url
     */
    public void toImge(HttpServletResponse response,String imgUrl){
        if(StringUtils.isBlank(imgUrl)){
            return;
        }
        String sufix = ImgeUtil.suffix(imgUrl);
        BufferedImage bi = ImgeUtil.requestRemoteImg(imgUrl);
        //1.设置响应头通知浏览器以图片的形式打开
        response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
        //2.设置响应头控制浏览器不要缓存
/*        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");*/
        //3.将图片写给浏览器
        try {
            ImageIO.write(bi, sufix, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件流输出
     * @param response
     * @param targetFilePath 文件地址
     */
    public void toFile(HttpServletResponse response,String targetFilePath){
        File file = new File(targetFilePath);
        toFile(response,file);
    }
    /**
     * 文件流输出
     * @param response
     * @param file 文件
     */
    public void toFile(HttpServletResponse response, File file) {
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[256];
            out = response.getOutputStream();
            while((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
        }catch(Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(in != null) {
                try {
                    in.close();
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

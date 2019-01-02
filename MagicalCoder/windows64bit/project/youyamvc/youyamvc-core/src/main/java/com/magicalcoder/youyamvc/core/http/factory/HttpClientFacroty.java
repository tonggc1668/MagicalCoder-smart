package com.magicalcoder.youyamvc.core.http.factory;


import com.magicalcoder.youyamvc.core.http.constant.HttpClientConstant;
import com.magicalcoder.youyamvc.core.http.utils.HttpClientGet;
import com.magicalcoder.youyamvc.core.http.utils.HttpClientMultipartPost;
import com.magicalcoder.youyamvc.core.http.utils.HttpClientPost;
import com.magicalcoder.youyamvc.core.http.utils.IHttpClient;

/**
 * Created by hzhedongyu on 2015/10/14.
 * 构造基础请求类
 */
public class HttpClientFacroty {

    public static IHttpClient create(String method){
        String lowerMethod = method.toLowerCase();
        if(HttpClientConstant.POST.equals(lowerMethod)){
            return HttpClientPost.get();
        }else if(HttpClientConstant.MULTIPART_POST.endsWith(lowerMethod)){
            return HttpClientMultipartPost.get();
        }else{
            return HttpClientGet.get();
        }
    }

    public static IHttpClient createPost(){
        return create(HttpClientConstant.POST);
    }
    public static IHttpClient createGet(){
        return create(HttpClientConstant.GET);
    }
    public static IHttpClient createMultipartPost(){
        return create(HttpClientConstant.MULTIPART_POST);
    }
}

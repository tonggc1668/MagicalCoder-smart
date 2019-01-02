package com.magicalcoder.youyamvc.app.userweb.constant;

/**
* Created by hdy.
* 799374340@qq.com
*/public interface UserWebConstant{
    int PAGE_SIZE = 20;
    int WAP_PAGE_SIZE = 10;
    int PAGE_MAX_SIZE = 20;
    int LOGIN_EXPIRE_TIME = 29*3600;//普通登录后存储的用户信息超时时间
    int LOGIN_SSO_EXPIRE_TIME = 30*3600;//单点登录存储的key超时时间
    //验证码使用类型
    String VALID_CODE_USE_REGISTER = "register";//注册
    String VALID_CODE_USE_CHANGE_MOBILE = "valid_change_mobile";//验证更改手机
    String VALID_CODE_USE_UPDATE_MOBILE = "update_mobile";//更改手机
    String VALID_CODE_USE_FIND_PASSWORD = "find_password";//找回密码


}

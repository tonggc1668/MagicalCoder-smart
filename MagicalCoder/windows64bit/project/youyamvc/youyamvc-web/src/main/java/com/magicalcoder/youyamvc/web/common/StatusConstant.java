package com.magicalcoder.youyamvc.web.common;

/**
 * Created by www.magicalcoder.com on 2015/7/15.
 * 799374340@qq.com
 */
public interface StatusConstant {

    int SUCCESS = 0;
    int VALID_PARAM = -1;//入参错误或不合法
    int NEED_LOGIN = -201;//未登录
    int LOGIN_FAIL = -202;//登录失败

    int ERROR = 500;
}

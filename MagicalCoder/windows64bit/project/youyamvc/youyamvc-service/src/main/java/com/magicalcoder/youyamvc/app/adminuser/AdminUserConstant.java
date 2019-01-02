package com.magicalcoder.youyamvc.app.adminuser;
public interface AdminUserConstant {
    int PAGE_SIZE = 20;
    String ADMIN_LOGIN_COOKIE_KEY = "sessionId";
    int ADMIN_LOGIN_TIMEOUT = 5*60*60;//登录后多久失效
}

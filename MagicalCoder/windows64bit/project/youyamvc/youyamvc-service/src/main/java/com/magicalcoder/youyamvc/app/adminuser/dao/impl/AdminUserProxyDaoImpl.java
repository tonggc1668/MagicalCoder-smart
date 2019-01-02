package com.magicalcoder.youyamvc.app.adminuser.dao.impl;

import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserProxyDao;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("adminUserProxyDao")
public class AdminUserProxyDaoImpl implements AdminUserProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

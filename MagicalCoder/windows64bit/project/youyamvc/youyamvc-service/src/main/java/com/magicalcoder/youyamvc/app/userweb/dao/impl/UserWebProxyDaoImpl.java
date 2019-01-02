package com.magicalcoder.youyamvc.app.userweb.dao.impl;

import com.magicalcoder.youyamvc.app.userweb.dao.UserWebProxyDao;
import com.magicalcoder.youyamvc.app.model.UserWeb;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("userWebProxyDao")
public class UserWebProxyDaoImpl implements UserWebProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

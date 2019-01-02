package com.magicalcoder.youyamvc.app.classes.dao.impl;

import com.magicalcoder.youyamvc.app.classes.dao.ClassesProxyDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("classesProxyDao")
public class ClassesProxyDaoImpl implements ClassesProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

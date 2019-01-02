package com.magicalcoder.youyamvc.app.classteacher.dao.impl;

import com.magicalcoder.youyamvc.app.classteacher.dao.ClassTeacherProxyDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("classTeacherProxyDao")
public class ClassTeacherProxyDaoImpl implements ClassTeacherProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

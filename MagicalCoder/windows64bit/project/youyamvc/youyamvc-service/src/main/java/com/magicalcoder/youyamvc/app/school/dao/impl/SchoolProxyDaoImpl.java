package com.magicalcoder.youyamvc.app.school.dao.impl;

import com.magicalcoder.youyamvc.app.school.dao.SchoolProxyDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("schoolProxyDao")
public class SchoolProxyDaoImpl implements SchoolProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

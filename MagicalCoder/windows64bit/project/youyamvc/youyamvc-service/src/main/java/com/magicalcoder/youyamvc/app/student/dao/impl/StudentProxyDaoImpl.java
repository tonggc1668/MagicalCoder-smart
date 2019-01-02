package com.magicalcoder.youyamvc.app.student.dao.impl;

import com.magicalcoder.youyamvc.app.student.dao.StudentProxyDao;
import com.magicalcoder.youyamvc.app.model.Student;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("studentProxyDao")
public class StudentProxyDaoImpl implements StudentProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

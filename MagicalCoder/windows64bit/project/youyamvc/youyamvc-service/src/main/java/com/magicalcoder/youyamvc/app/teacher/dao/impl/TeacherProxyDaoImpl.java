package com.magicalcoder.youyamvc.app.teacher.dao.impl;

import com.magicalcoder.youyamvc.app.teacher.dao.TeacherProxyDao;
import com.magicalcoder.youyamvc.app.model.Teacher;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("teacherProxyDao")
public class TeacherProxyDaoImpl implements TeacherProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

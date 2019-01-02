package com.magicalcoder.youyamvc.app.teacher.service.impl;

import com.magicalcoder.youyamvc.app.teacher.dao.TeacherProxyDao;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherService;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherProxyService;
import com.magicalcoder.youyamvc.app.model.Teacher;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;

/**
    代理类 保证了service dao的自动生成 有改动业务 直接写在此类处
*/
/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("teacherProxyService")
public class TeacherProxyServiceImpl implements TeacherProxyService{

    @Resource(name="teacherProxyDao")
    private TeacherProxyDao teacherProxyDao;

    @Resource(name="teacherService")
    private TeacherService teacherService;
 

}

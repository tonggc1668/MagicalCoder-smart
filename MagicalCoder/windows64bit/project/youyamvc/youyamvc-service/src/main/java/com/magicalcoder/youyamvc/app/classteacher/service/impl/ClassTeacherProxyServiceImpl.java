package com.magicalcoder.youyamvc.app.classteacher.service.impl;

import com.magicalcoder.youyamvc.app.classteacher.dao.ClassTeacherProxyDao;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherService;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherProxyService;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;

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
@Component("classTeacherProxyService")
public class ClassTeacherProxyServiceImpl implements ClassTeacherProxyService{

    @Resource(name="classTeacherProxyDao")
    private ClassTeacherProxyDao classTeacherProxyDao;

    @Resource(name="classTeacherService")
    private ClassTeacherService classTeacherService;
 

}

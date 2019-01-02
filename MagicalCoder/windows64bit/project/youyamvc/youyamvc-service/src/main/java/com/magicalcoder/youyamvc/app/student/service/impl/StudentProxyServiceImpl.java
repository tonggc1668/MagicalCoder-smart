package com.magicalcoder.youyamvc.app.student.service.impl;

import com.magicalcoder.youyamvc.app.student.dao.StudentProxyDao;
import com.magicalcoder.youyamvc.app.student.service.StudentService;
import com.magicalcoder.youyamvc.app.student.service.StudentProxyService;
import com.magicalcoder.youyamvc.app.model.Student;

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
@Component("studentProxyService")
public class StudentProxyServiceImpl implements StudentProxyService{

    @Resource(name="studentProxyDao")
    private StudentProxyDao studentProxyDao;

    @Resource(name="studentService")
    private StudentService studentService;
 

}

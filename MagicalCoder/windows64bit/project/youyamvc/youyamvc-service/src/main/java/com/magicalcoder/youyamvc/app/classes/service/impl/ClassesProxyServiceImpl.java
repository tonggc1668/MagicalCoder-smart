package com.magicalcoder.youyamvc.app.classes.service.impl;

import com.magicalcoder.youyamvc.app.classes.dao.ClassesProxyDao;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.classes.service.ClassesProxyService;
import com.magicalcoder.youyamvc.app.model.Classes;

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
@Component("classesProxyService")
public class ClassesProxyServiceImpl implements ClassesProxyService{

    @Resource(name="classesProxyDao")
    private ClassesProxyDao classesProxyDao;

    @Resource(name="classesService")
    private ClassesService classesService;
 

}

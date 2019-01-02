package com.magicalcoder.youyamvc.app.module.service.impl;

import com.magicalcoder.youyamvc.app.module.dao.ModuleProxyDao;
import com.magicalcoder.youyamvc.app.module.service.ModuleService;
import com.magicalcoder.youyamvc.app.module.service.ModuleProxyService;
import com.magicalcoder.youyamvc.app.model.Module;

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
@Component("moduleProxyService")
public class ModuleProxyServiceImpl implements ModuleProxyService{

    @Resource(name="moduleProxyDao")
    private ModuleProxyDao moduleProxyDao;

    @Resource(name="moduleService")
    private ModuleService moduleService;
 

}

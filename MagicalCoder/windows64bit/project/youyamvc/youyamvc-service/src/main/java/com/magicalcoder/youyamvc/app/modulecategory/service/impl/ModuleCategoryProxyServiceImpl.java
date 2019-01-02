package com.magicalcoder.youyamvc.app.modulecategory.service.impl;

import com.magicalcoder.youyamvc.app.modulecategory.dao.ModuleCategoryProxyDao;
import com.magicalcoder.youyamvc.app.modulecategory.service.ModuleCategoryService;
import com.magicalcoder.youyamvc.app.modulecategory.service.ModuleCategoryProxyService;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;

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
@Component("moduleCategoryProxyService")
public class ModuleCategoryProxyServiceImpl implements ModuleCategoryProxyService{

    @Resource(name="moduleCategoryProxyDao")
    private ModuleCategoryProxyDao moduleCategoryProxyDao;

    @Resource(name="moduleCategoryService")
    private ModuleCategoryService moduleCategoryService;
 

}

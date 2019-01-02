package com.magicalcoder.youyamvc.app.module.dao.impl;

import com.magicalcoder.youyamvc.app.module.dao.ModuleProxyDao;
import com.magicalcoder.youyamvc.app.model.Module;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("moduleProxyDao")
public class ModuleProxyDaoImpl implements ModuleProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

package com.magicalcoder.youyamvc.app.rolemodulepriority.dao.impl;

import com.magicalcoder.youyamvc.app.model.Module;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.app.rolemodulepriority.dao.RoleModulePriorityProxyDao;
import com.magicalcoder.youyamvc.app.model.RoleModulePriority;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("roleModulePriorityProxyDao")
public class RoleModulePriorityProxyDaoImpl implements RoleModulePriorityProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;

    @Override
    public List<Module> roleHasModuleList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("RoleModulePriorityProxyMapper.roleHasModuleList",query);
    }

    @Override
    public Priority roleModuleHasPriority(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("RoleModulePriorityProxyMapper.roleModuleHasPriority", query);
    }
}

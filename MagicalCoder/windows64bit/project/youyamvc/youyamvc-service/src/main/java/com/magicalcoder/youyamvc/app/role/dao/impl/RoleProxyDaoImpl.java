package com.magicalcoder.youyamvc.app.role.dao.impl;

import com.magicalcoder.youyamvc.app.role.dao.RoleProxyDao;
import com.magicalcoder.youyamvc.app.model.Role;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("roleProxyDao")
public class RoleProxyDaoImpl implements RoleProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

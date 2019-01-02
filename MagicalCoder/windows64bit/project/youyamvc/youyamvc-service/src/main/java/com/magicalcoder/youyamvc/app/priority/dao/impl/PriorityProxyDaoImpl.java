package com.magicalcoder.youyamvc.app.priority.dao.impl;

import com.magicalcoder.youyamvc.app.priority.dao.PriorityProxyDao;
import com.magicalcoder.youyamvc.app.model.Priority;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("priorityProxyDao")
public class PriorityProxyDaoImpl implements PriorityProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}

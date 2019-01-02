package com.magicalcoder.youyamvc.app.priority.service.impl;

import com.magicalcoder.youyamvc.app.priority.dao.PriorityProxyDao;
import com.magicalcoder.youyamvc.app.priority.service.PriorityService;
import com.magicalcoder.youyamvc.app.priority.service.PriorityProxyService;
import com.magicalcoder.youyamvc.app.model.Priority;

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
@Component("priorityProxyService")
public class PriorityProxyServiceImpl implements PriorityProxyService{

    @Resource(name="priorityProxyDao")
    private PriorityProxyDao priorityProxyDao;

    @Resource(name="priorityService")
    private PriorityService priorityService;
 

}

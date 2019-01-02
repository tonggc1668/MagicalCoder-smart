package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class RoleModulePriority implements Serializable{

    private Long id;//主键
    private Long roleId;//角色
    private Long moduleId;//模块
    private Long priorityId;//权限

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public Long getRoleId(){
        return roleId;
    }
    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
    public Long getModuleId(){
        return moduleId;
    }
    public void setModuleId(Long moduleId){
        this.moduleId = moduleId;
    }
    public Long getPriorityId(){
        return priorityId;
    }
    public void setPriorityId(Long priorityId){
        this.priorityId = priorityId;
    }

}

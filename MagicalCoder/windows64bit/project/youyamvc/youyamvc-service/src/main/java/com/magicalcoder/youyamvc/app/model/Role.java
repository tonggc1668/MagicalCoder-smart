package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Role implements Serializable{

    private Long id;//主键
    private String roleName;//角色名

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getRoleName(){
        return roleName;
    }
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

}

package com.magicalcoder.youyamvc.app.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class AdminUser implements Serializable{

    private Long id;//主键
    private String userName;//用户名
    private String password;//密码
    private String realName;//真名
    private String email;//邮箱
    private String telephone;//座机号
    private String mobilePhone;//手机号
    private String address;//手机号
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;//创建时间
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;//更新时间
    private Integer superAdmin;//是否超级管理员
    private Long roleId;//角色

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getRealName(){
        return realName;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getTelephone(){
        return telephone;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public String getMobilePhone(){
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone){
        this.mobilePhone = mobilePhone;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    public Integer getSuperAdmin(){
        return superAdmin;
    }
    public void setSuperAdmin(Integer superAdmin){
        this.superAdmin = superAdmin;
    }
    public Long getRoleId(){
        return roleId;
    }
    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }

}

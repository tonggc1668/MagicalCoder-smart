package com.magicalcoder.youyamvc.app.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Student implements Serializable{

    private String name;//学生名称
    private Long classId;//所属班级
    private Integer sex;//性别
    private Integer id;//主键
    private Long adminUserId;//管理员
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;//创建时间
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;//更新时间

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Long getClassId(){
        return classId;
    }
    public void setClassId(Long classId){
        this.classId = classId;
    }
    public Integer getSex(){
        return sex;
    }
    public void setSex(Integer sex){
        this.sex = sex;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public Long getAdminUserId(){
        return adminUserId;
    }
    public void setAdminUserId(Long adminUserId){
        this.adminUserId = adminUserId;
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

}

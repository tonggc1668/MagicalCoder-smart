package com.magicalcoder.youyamvc.app.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class School implements Serializable{

    private Long id;//学校主键
    private String schoolName;//学校名称
    private String headImg;//学校头像
    private Integer classCount;//班级个数
    private String adress;//学校地址
    private Integer schoolType;//学校类型
    private Boolean open;//是否开学
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;//创建时间
    private String schoolDesc;//学校描述
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;//更新时间

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getSchoolName(){
        return schoolName;
    }
    public void setSchoolName(String schoolName){
        this.schoolName = schoolName;
    }
    public String getHeadImg(){
        return headImg;
    }
    public void setHeadImg(String headImg){
        this.headImg = headImg;
    }
    public Integer getClassCount(){
        return classCount;
    }
    public void setClassCount(Integer classCount){
        this.classCount = classCount;
    }
    public String getAdress(){
        return adress;
    }
    public void setAdress(String adress){
        this.adress = adress;
    }
    public Integer getSchoolType(){
        return schoolType;
    }
    public void setSchoolType(Integer schoolType){
        this.schoolType = schoolType;
    }
    public Boolean getOpen(){
        return open;
    }
    public void setOpen(Boolean open){
        this.open = open;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public String getSchoolDesc(){
        return schoolDesc;
    }
    public void setSchoolDesc(String schoolDesc){
        this.schoolDesc = schoolDesc;
    }
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}

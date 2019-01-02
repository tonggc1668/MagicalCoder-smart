package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Teacher implements Serializable{

    private Long id;//老师主键
    private String teacherName;//老师名称
    private Integer age;//老师年龄

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getTeacherName(){
        return teacherName;
    }
    public void setTeacherName(String teacherName){
        this.teacherName = teacherName;
    }
    public Integer getAge(){
        return age;
    }
    public void setAge(Integer age){
        this.age = age;
    }

}

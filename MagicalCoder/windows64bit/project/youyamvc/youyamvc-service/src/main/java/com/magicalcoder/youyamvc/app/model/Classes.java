package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Classes implements Serializable{

    private Long id;//班级主键
    private String className;//班级名称
    private Integer studentCount;//班级学生人数
    private Long schoolId;//学校id

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getClassName(){
        return className;
    }
    public void setClassName(String className){
        this.className = className;
    }
    public Integer getStudentCount(){
        return studentCount;
    }
    public void setStudentCount(Integer studentCount){
        this.studentCount = studentCount;
    }
    public Long getSchoolId(){
        return schoolId;
    }
    public void setSchoolId(Long schoolId){
        this.schoolId = schoolId;
    }

}

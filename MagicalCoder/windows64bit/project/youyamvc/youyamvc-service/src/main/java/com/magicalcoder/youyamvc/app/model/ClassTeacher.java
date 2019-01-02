package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class ClassTeacher implements Serializable{

    private Long id;//班级关联老师表主键
    private Long classId;//班级表主键
    private Long teacherId;//老师表主键

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public Long getClassId(){
        return classId;
    }
    public void setClassId(Long classId){
        this.classId = classId;
    }
    public Long getTeacherId(){
        return teacherId;
    }
    public void setTeacherId(Long teacherId){
        this.teacherId = teacherId;
    }

}

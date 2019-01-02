package com.magicalcoder.youyamvc.app.student.dto;

import com.magicalcoder.youyamvc.app.model.Student;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
public class StudentDto extends Student {

    private String nameOldValue;
    private Long classIdOldValue;

    public String getNameOldValue() {
        return nameOldValue;
    }

    public void setNameOldValue(String nameOldValue) {
        this.nameOldValue = nameOldValue;
    }

    public Long getClassIdOldValue() {
        return classIdOldValue;
    }

    public void setClassIdOldValue(Long classIdOldValue) {
        this.classIdOldValue = classIdOldValue;
    }
}

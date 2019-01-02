package com.magicalcoder.youyamvc.app.model.defined;

import com.magicalcoder.youyamvc.app.model.Teacher;
/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class ClassTeacherMany extends Teacher{

    private Long teacherId;//老师表主键

    public Long getTeacherId(){
        return teacherId;
    }

    public void setTeacherId(Long teacherId){
        this.teacherId = teacherId;
    }

}

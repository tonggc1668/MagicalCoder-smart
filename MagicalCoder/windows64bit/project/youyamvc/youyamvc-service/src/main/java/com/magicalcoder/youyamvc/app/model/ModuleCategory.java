package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class ModuleCategory implements Serializable{

    private Long id;//主键
    private String moduleCategoryName;//模块名称
    private Integer sortNum;//排序

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getModuleCategoryName(){
        return moduleCategoryName;
    }
    public void setModuleCategoryName(String moduleCategoryName){
        this.moduleCategoryName = moduleCategoryName;
    }
    public Integer getSortNum(){
        return sortNum;
    }
    public void setSortNum(Integer sortNum){
        this.sortNum = sortNum;
    }

}

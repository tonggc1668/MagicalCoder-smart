package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Module implements Serializable{

    private Long id;//主键
    private String moduleName;//模块唯一键
    private String moduleUrl;//模块url
    private Long moduleCategoryId;//模块分类
    private Integer sortNum;//排序
    private String moduleTitle;//模块标题

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getModuleName(){
        return moduleName;
    }
    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
    public String getModuleUrl(){
        return moduleUrl;
    }
    public void setModuleUrl(String moduleUrl){
        this.moduleUrl = moduleUrl;
    }
    public Long getModuleCategoryId(){
        return moduleCategoryId;
    }
    public void setModuleCategoryId(Long moduleCategoryId){
        this.moduleCategoryId = moduleCategoryId;
    }
    public Integer getSortNum(){
        return sortNum;
    }
    public void setSortNum(Integer sortNum){
        this.sortNum = sortNum;
    }
    public String getModuleTitle(){
        return moduleTitle;
    }
    public void setModuleTitle(String moduleTitle){
        this.moduleTitle = moduleTitle;
    }

}

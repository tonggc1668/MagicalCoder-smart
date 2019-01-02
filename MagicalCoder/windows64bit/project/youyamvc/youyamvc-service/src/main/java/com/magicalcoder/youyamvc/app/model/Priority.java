package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Priority implements Serializable{

    private Long id;//主键
    private String priorityName;//权限名
    private Boolean canInsert;//新增
    private Boolean canDelete;//删除
    private Boolean canUpdate;//编辑
    private Boolean canQuery;//查询
    private Boolean canTruncate;//清空
    private Boolean canExport;//导出
    private Boolean canImport;//导入

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getPriorityName(){
        return priorityName;
    }
    public void setPriorityName(String priorityName){
        this.priorityName = priorityName;
    }
    public Boolean getCanInsert(){
        return canInsert;
    }
    public void setCanInsert(Boolean canInsert){
        this.canInsert = canInsert;
    }
    public Boolean getCanDelete(){
        return canDelete;
    }
    public void setCanDelete(Boolean canDelete){
        this.canDelete = canDelete;
    }
    public Boolean getCanUpdate(){
        return canUpdate;
    }
    public void setCanUpdate(Boolean canUpdate){
        this.canUpdate = canUpdate;
    }
    public Boolean getCanQuery(){
        return canQuery;
    }
    public void setCanQuery(Boolean canQuery){
        this.canQuery = canQuery;
    }
    public Boolean getCanTruncate(){
        return canTruncate;
    }
    public void setCanTruncate(Boolean canTruncate){
        this.canTruncate = canTruncate;
    }
    public Boolean getCanExport(){
        return canExport;
    }
    public void setCanExport(Boolean canExport){
        this.canExport = canExport;
    }
    public Boolean getCanImport(){
        return canImport;
    }
    public void setCanImport(Boolean canImport){
        this.canImport = canImport;
    }

}

package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Dict implements Serializable{

    private Long id;//
    private String dictKey;//
    private String dictValue;//
    private Integer dictType;//
    private String dictDesc;//
    private Integer dictOrder;//

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getDictKey(){
        return dictKey;
    }
    public void setDictKey(String dictKey){
        this.dictKey = dictKey;
    }
    public String getDictValue(){
        return dictValue;
    }
    public void setDictValue(String dictValue){
        this.dictValue = dictValue;
    }
    public Integer getDictType(){
        return dictType;
    }
    public void setDictType(Integer dictType){
        this.dictType = dictType;
    }
    public String getDictDesc(){
        return dictDesc;
    }
    public void setDictDesc(String dictDesc){
        this.dictDesc = dictDesc;
    }
    public Integer getDictOrder(){
        return dictOrder;
    }
    public void setDictOrder(Integer dictOrder){
        this.dictOrder = dictOrder;
    }

}

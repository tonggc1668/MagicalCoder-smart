package com.magicalcoder.youyamvc.app.adminuser.dto;

import com.magicalcoder.youyamvc.app.model.Module;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class ModuleAndCategoryDto implements Serializable{
    private ModuleCategory category;
    private List<Module> moduleList;

    public ModuleCategory getCategory() {
        return category;
    }

    public void setCategory(ModuleCategory category) {
        this.category = category;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}

package com.magicalcoder.youyamvc.app.adminuser.dto;

import com.magicalcoder.youyamvc.app.model.AdminUser;

import java.util.List;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
public class AdminUserDto extends AdminUser {

    List<ModuleAndCategoryDto> userLeftModules;

    public List<ModuleAndCategoryDto> getUserLeftModules() {
        return userLeftModules;
    }

    public void setUserLeftModules(List<ModuleAndCategoryDto> userLeftModules) {
        this.userLeftModules = userLeftModules;
    }
}

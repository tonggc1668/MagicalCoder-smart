package com.magicalcoder.youyamvc.web.controller.admin.adminuser;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserProxyService;
import com.magicalcoder.youyamvc.app.adminuser.constant.AdminUserConstant;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserUtil;
import com.magicalcoder.youyamvc.app.adminuser.dto.AdminUserDto;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.common.dto.InputSelectShowDto;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserContextUtil;
import com.magicalcoder.youyamvc.app.model.Role;
import com.magicalcoder.youyamvc.app.role.service.RoleService;
import java.io.File;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.common.utils.serialize.SerializerFastJsonUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;
import java.math.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/admin/admin_user"})
@Controller
public class AdminAdminUserListController extends AdminLoginController
{
    private final String moduleName = "admin_user";
    @Resource
    private RoleService roleService;

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private AdminUserProxyService adminUserProxyService;
    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        List<Role> roleList =
                    roleService.getRoleList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("roleList", roleList);
        return "admin/adminuser/adminUserList";
    }

    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="idFirst")                        Long idFirst ,
        @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
        @RequestParam(required = false,value ="realNameFirst")                        String realNameFirst ,
        @RequestParam(required = false,value ="emailFirst")                        String emailFirst ,
        @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeFirst ,
        @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeSecond ,
        @RequestParam(required = false,value ="updateTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTimeFirst ,
        @RequestParam(required = false,value ="updateTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTimeSecond ,
        @RequestParam(required = false,value ="superAdminFirst")                        Integer superAdminFirst ,
        @RequestParam(required = false,value ="roleIdFirst")                        Long roleIdFirst ,
        HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,user_name,password,real_name,email,telephone,mobile_phone,address,create_time,update_time,super_admin,role_id,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        pageSize = Math.min(AdminUserConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "idFirst",idFirst ,
                "userNameFirst",userNameFirst ,
                "realNameFirst",realNameFirst ,
                "emailFirst",emailFirst ,
                "createTimeFirst",createTimeFirst ,
                "createTimeSecond",createTimeSecond ,
                "updateTimeFirst",updateTimeFirst ,
                "updateTimeSecond",updateTimeSecond ,
                "superAdminFirst",superAdminFirst ,
                "roleIdFirst",roleIdFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.adminUserService.getAdminUserList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.adminUserService.getAdminUserListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toWebSuccessJson(response, ajaxData);
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<AdminUser> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理roleId外键
            StringBuffer roleIds = new StringBuffer();
            for(AdminUser item : pageList){
                if(!roleIds.toString().contains(","+item.getRoleId()+",")){
                         roleIds.append(item.getRoleId()).append(",");
                }
            }

            List<Role> roleList = roleService.getRoleList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(roleIds.toString())+")"));
            Map<Long,Role> roleMap = new HashMap<Long, Role>();
            if(ListUtils.isNotBlank(roleList)){
                for(Role entity:roleList){
                    roleMap.put(entity.getId(),entity);
                }
            }

            //使用索引替换外键展示值
            for(AdminUser item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                obj.put("createTime",item.getCreateTime());
                obj.put("updateTime",item.getUpdateTime());
                Long roleId = item.getRoleId();
                Role role = roleMap.get(roleId);
                String roleIdForeignShowValue = role==null?"":""+role.getRoleName();
                obj.put("roleIdForeignShowValue",roleIdForeignShowValue);
                newPageList.add(obj);
            }
        }
        return newPageList;
    }
    //根据自定义查询条件到编辑
    @RequestMapping({"/detail_param"})
    public String detailId(HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        Map<String,Object> reqMap = ProjectUtil.getParams(request);
        AdminUser entity = this.adminUserService.selectOneAdminUserWillThrowException(reqMap);
        model.addAttribute("adminUser", entity);
        foreignModel(entity,model);
        return "admin/adminuser/adminUserDetail";
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail( HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
            return "admin/priorityAlert";
        }
        model.addAttribute("adminUser", new AdminUser());
        return "admin/adminuser/adminUserDetail";
    }

    private void foreignModel(AdminUser entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getRoleId());
                Role role= roleService.selectOneRoleWillThrowException(map);
            model.addAttribute("role",role);
    }

    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
        public String detailId(@PathVariable Long id,
        HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        AdminUser entity = this.adminUserService.getAdminUser(id);
        model.addAttribute("adminUser", entity);
        foreignModel(entity,model);
        return "admin/adminuser/adminUserDetail";
    }

    //ajax更新状态
    @RequestMapping(value="ajax_update", method={RequestMethod.POST})
    public void ajaxUpdate(@ModelAttribute AdminUser adminUser,
        HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        try{
            this.adminUserService.updateAdminUserWithoutNull(adminUser);
            toWebSuccessJson(response);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            toWebFailureJson(response,exceptionMsg);
        }
    }
    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute AdminUser adminUser,
        HttpServletRequest request,ModelMap model) {
        try{
            model.addAttribute("adminUser",adminUser);
            foreignModel(adminUser,model);
            Date now = new Date();
                    adminUser.setUpdateTime(now);
            if (adminUser.getId() == null) {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
                    return "admin/priorityAlert";
                }
                        adminUser.setCreateTime(now);
                this.adminUserService.insertAdminUser(adminUser);
            } else {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
                    return "admin/priorityAlert";
                }
                AdminUser entity = this.adminUserService.getAdminUser(adminUser.getId());
                        adminUser.setCreateTime(entity.getCreateTime());
                Copyer.copy(adminUser, entity);
                this.adminUserService.updateAdminUser(entity);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/adminuser/adminUserDetail";
        }
        return "redirect:/admin/admin_user/list";
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id,
        HttpServletRequest request, HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanDelete()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        this.adminUserService.deleteAdminUser(id);
        toWebSuccessJson(response);
    }
    //批量删除
    @RequestMapping({"/batchdelete/{ids}"})
    public void batchDelete(@PathVariable String ids, HttpServletRequest request,HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanDelete()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        if(StringUtils.isNotBlank(ids)){
            String[] idArr = ids.split(",");
            List<Long> list = new ArrayList<Long>();
            for(String id:idArr){
                if(StringUtils.isNotBlank(id)){
                    list.add(Long.valueOf(id));
                }
            }
            if(ListUtils.isNotBlank(list)){
                this.adminUserService.batchDeleteAdminUser(list);
                toWebSuccessJson(response);
            }
        }else{
            toWebFailureJson(response,"没有要删除的主键");
        }
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletRequest request,HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanTruncate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        this.adminUserService.truncateAdminUser();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletRequest request,HttpServletResponse response) throws IOException {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanImport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<AdminUser> list = SerializerFastJsonUtil.get().readJsonList(fileContent,AdminUser.class);
            if(ListUtils.isNotBlank(list)){
                this.adminUserService.transactionImportJsonList(list);
            }
            toWebSuccessJson(response);
        }else {
            toWebFailureJson(response, "不支持的文件后缀");
        }
    }
    //json文件导出
    @RequestMapping(value = "export/json/{start}/{limit}",method = RequestMethod.GET)
    public void exportJson(@PathVariable Integer start,@PathVariable Integer limit,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="idFirst")                        Long idFirst ,
        @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
        @RequestParam(required = false,value ="realNameFirst")                        String realNameFirst ,
        @RequestParam(required = false,value ="emailFirst")                        String emailFirst ,
        @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeFirst ,
        @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeSecond ,
        @RequestParam(required = false,value ="updateTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTimeFirst ,
        @RequestParam(required = false,value ="updateTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTimeSecond ,
        @RequestParam(required = false,value ="superAdminFirst")                        Integer superAdminFirst ,
        @RequestParam(required = false,value ="roleIdFirst")                        Long roleIdFirst ,
        HttpServletRequest request,HttpServletResponse response){
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanExport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,user_name,password,real_name,email,telephone,mobile_phone,address,create_time,update_time,super_admin,role_id,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "idFirst",idFirst ,
                "userNameFirst",userNameFirst ,
                "realNameFirst",realNameFirst ,
                "emailFirst",emailFirst ,
                "createTimeFirst",createTimeFirst ,
                "createTimeSecond",createTimeSecond ,
                "updateTimeFirst",updateTimeFirst ,
                "updateTimeSecond",updateTimeSecond ,
                "superAdminFirst",superAdminFirst ,
                "roleIdFirst",roleIdFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.adminUserService.getAdminUserList(query);

        String file = "admin_user";
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(file,".txt");
            String json = JSON.toJSONString(pageList, true);
            FileHelper.fastWriteFileUTF8(tmpFile, json);
            toFile(response, tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(tmpFile!=null){
                tmpFile.delete();
            }
        }
    }


//===================搜索下拉框 外键查询使用begin=================================
    @RequestMapping(value = "type_ahead_search",method = RequestMethod.GET)
    public void typeAheadSearch(@RequestParam(value = "keyword",required = false) String keyword,
        @RequestParam(value = "foreignJavaField",required = false) String foreignJavaField,
        @RequestParam(value = "selectValue",required = false) String selectValue,
        HttpServletResponse response){
        if(StringUtils.isBlank(selectValue)){
            StringBuffer sb = new StringBuffer();
            sb.append("userName").append(",");
            sb.append("realName").append(",");
            selectValue = StringUtils.deleteLastChar(sb.toString());
        }
        List<AdminUser> list = adminUserService.tryQueryList(keyword,selectValue,foreignJavaField);
        toSimpleJson(response,ProjectUtil.showList(list,selectValue,foreignJavaField));
    }
    //===================end=================================

}

package com.magicalcoder.youyamvc.web.controller.admin.rolemodulepriority;
import com.magicalcoder.youyamvc.app.rolemodulepriority.service.RoleModulePriorityService;
import com.magicalcoder.youyamvc.app.rolemodulepriority.service.RoleModulePriorityProxyService;
import com.magicalcoder.youyamvc.app.rolemodulepriority.constant.RoleModulePriorityConstant;
import com.magicalcoder.youyamvc.app.rolemodulepriority.util.RoleModulePriorityUtil;
import com.magicalcoder.youyamvc.app.rolemodulepriority.dto.RoleModulePriorityDto;
import com.magicalcoder.youyamvc.app.model.RoleModulePriority;
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
import com.magicalcoder.youyamvc.app.model.Module;
import com.magicalcoder.youyamvc.app.module.service.ModuleService;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.app.priority.service.PriorityService;
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
@RequestMapping({"/admin/role_module_priority"})
@Controller
public class AdminRoleModulePriorityListController extends AdminLoginController
{
    private final String moduleName = "role_module_priority";
    @Resource
    private RoleService roleService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private PriorityService priorityService;

    @Resource
    private RoleModulePriorityService roleModulePriorityService;
    @Resource
    private RoleModulePriorityProxyService roleModulePriorityProxyService;
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
        List<Module> moduleList =
                    moduleService.getModuleList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("moduleList", moduleList);
        List<Priority> priorityList =
                    priorityService.getPriorityList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("priorityList", priorityList);
        return "admin/rolemodulepriority/roleModulePriorityList";
    }

    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="roleIdFirst")                        Long roleIdFirst ,
        @RequestParam(required = false,value ="moduleIdFirst")                        Long moduleIdFirst ,
        @RequestParam(required = false,value ="priorityIdFirst")                        Long priorityIdFirst ,
        HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,role_id,module_id,priority_id,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        pageSize = Math.min(RoleModulePriorityConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "roleIdFirst",roleIdFirst ,
                "moduleIdFirst",moduleIdFirst ,
                "priorityIdFirst",priorityIdFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.roleModulePriorityService.getRoleModulePriorityList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.roleModulePriorityService.getRoleModulePriorityListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toWebSuccessJson(response, ajaxData);
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<RoleModulePriority> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理roleId外键
            StringBuffer roleIds = new StringBuffer();
            for(RoleModulePriority item : pageList){
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
            //处理moduleId外键
            StringBuffer moduleIds = new StringBuffer();
            for(RoleModulePriority item : pageList){
                if(!moduleIds.toString().contains(","+item.getModuleId()+",")){
                         moduleIds.append(item.getModuleId()).append(",");
                }
            }

            List<Module> moduleList = moduleService.getModuleList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(moduleIds.toString())+")"));
            Map<Long,Module> moduleMap = new HashMap<Long, Module>();
            if(ListUtils.isNotBlank(moduleList)){
                for(Module entity:moduleList){
                    moduleMap.put(entity.getId(),entity);
                }
            }
            //处理priorityId外键
            StringBuffer priorityIds = new StringBuffer();
            for(RoleModulePriority item : pageList){
                if(!priorityIds.toString().contains(","+item.getPriorityId()+",")){
                         priorityIds.append(item.getPriorityId()).append(",");
                }
            }

            List<Priority> priorityList = priorityService.getPriorityList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(priorityIds.toString())+")"));
            Map<Long,Priority> priorityMap = new HashMap<Long, Priority>();
            if(ListUtils.isNotBlank(priorityList)){
                for(Priority entity:priorityList){
                    priorityMap.put(entity.getId(),entity);
                }
            }

            //使用索引替换外键展示值
            for(RoleModulePriority item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                Long roleId = item.getRoleId();
                Role role = roleMap.get(roleId);
                String roleIdForeignShowValue = role==null?"":""+role.getRoleName();
                obj.put("roleIdForeignShowValue",roleIdForeignShowValue);
                Long moduleId = item.getModuleId();
                Module module = moduleMap.get(moduleId);
                String moduleIdForeignShowValue = module==null?"":""+module.getModuleName()+"-"+module.getModuleTitle();
                obj.put("moduleIdForeignShowValue",moduleIdForeignShowValue);
                Long priorityId = item.getPriorityId();
                Priority priority = priorityMap.get(priorityId);
                String priorityIdForeignShowValue = priority==null?"":""+priority.getPriorityName();
                obj.put("priorityIdForeignShowValue",priorityIdForeignShowValue);
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
        RoleModulePriority entity = this.roleModulePriorityService.selectOneRoleModulePriorityWillThrowException(reqMap);
        model.addAttribute("roleModulePriority", entity);
        foreignModel(entity,model);
        return "admin/rolemodulepriority/roleModulePriorityDetail";
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail( HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
            return "admin/priorityAlert";
        }
        model.addAttribute("roleModulePriority", new RoleModulePriority());
        return "admin/rolemodulepriority/roleModulePriorityDetail";
    }

    private void foreignModel(RoleModulePriority entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getRoleId());
                Role role= roleService.selectOneRoleWillThrowException(map);
            model.addAttribute("role",role);
            map = ProjectUtil.buildMap("id",entity.getModuleId());
                Module module= moduleService.selectOneModuleWillThrowException(map);
            model.addAttribute("module",module);
            map = ProjectUtil.buildMap("id",entity.getPriorityId());
                Priority priority= priorityService.selectOnePriorityWillThrowException(map);
            model.addAttribute("priority",priority);
    }

    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
        public String detailId(@PathVariable Long id,
        HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        RoleModulePriority entity = this.roleModulePriorityService.getRoleModulePriority(id);
        model.addAttribute("roleModulePriority", entity);
        foreignModel(entity,model);
        return "admin/rolemodulepriority/roleModulePriorityDetail";
    }

    //ajax更新状态
    @RequestMapping(value="ajax_update", method={RequestMethod.POST})
    public void ajaxUpdate(@ModelAttribute RoleModulePriority roleModulePriority,
        HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        try{
            this.roleModulePriorityService.updateRoleModulePriorityWithoutNull(roleModulePriority);
            toWebSuccessJson(response);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            toWebFailureJson(response,exceptionMsg);
        }
    }
    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute RoleModulePriority roleModulePriority,
        HttpServletRequest request,ModelMap model) {
        try{
            model.addAttribute("roleModulePriority",roleModulePriority);
            foreignModel(roleModulePriority,model);
            Date now = new Date();
            if (roleModulePriority.getId() == null) {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
                    return "admin/priorityAlert";
                }
                this.roleModulePriorityService.insertRoleModulePriority(roleModulePriority);
            } else {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
                    return "admin/priorityAlert";
                }
                RoleModulePriority entity = this.roleModulePriorityService.getRoleModulePriority(roleModulePriority.getId());
                Copyer.copy(roleModulePriority, entity);
                this.roleModulePriorityService.updateRoleModulePriority(entity);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/rolemodulepriority/roleModulePriorityDetail";
        }
        return "redirect:/admin/role_module_priority/list";
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
        this.roleModulePriorityService.deleteRoleModulePriority(id);
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
                this.roleModulePriorityService.batchDeleteRoleModulePriority(list);
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
        this.roleModulePriorityService.truncateRoleModulePriority();
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
            List<RoleModulePriority> list = SerializerFastJsonUtil.get().readJsonList(fileContent,RoleModulePriority.class);
            if(ListUtils.isNotBlank(list)){
                this.roleModulePriorityService.transactionImportJsonList(list);
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
        @RequestParam(required = false,value ="roleIdFirst")                        Long roleIdFirst ,
        @RequestParam(required = false,value ="moduleIdFirst")                        Long moduleIdFirst ,
        @RequestParam(required = false,value ="priorityIdFirst")                        Long priorityIdFirst ,
        HttpServletRequest request,HttpServletResponse response){
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanExport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,role_id,module_id,priority_id,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "roleIdFirst",roleIdFirst ,
                "moduleIdFirst",moduleIdFirst ,
                "priorityIdFirst",priorityIdFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.roleModulePriorityService.getRoleModulePriorityList(query);

        String file = "role_module_priority";
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
            selectValue = StringUtils.deleteLastChar(sb.toString());
        }
        List<RoleModulePriority> list = roleModulePriorityService.tryQueryList(keyword,selectValue,foreignJavaField);
        toSimpleJson(response,ProjectUtil.showList(list,selectValue,foreignJavaField));
    }
    //===================end=================================

}

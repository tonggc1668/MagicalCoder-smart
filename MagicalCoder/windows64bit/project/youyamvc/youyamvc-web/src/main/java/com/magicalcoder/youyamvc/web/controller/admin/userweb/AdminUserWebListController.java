package com.magicalcoder.youyamvc.web.controller.admin.userweb;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebProxyService;
import com.magicalcoder.youyamvc.app.userweb.constant.UserWebConstant;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;
import com.magicalcoder.youyamvc.app.userweb.dto.UserWebDto;
import com.magicalcoder.youyamvc.app.model.UserWeb;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.common.dto.InputSelectShowDto;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserContextUtil;
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
@RequestMapping({"/admin/user_web"})
@Controller
public class AdminUserWebListController extends AdminLoginController
{
    private final String moduleName = "user_web";

    @Resource
    private UserWebService userWebService;
    @Resource
    private UserWebProxyService userWebProxyService;
    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        return "admin/userweb/userWebList";
    }

    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
        @RequestParam(required = false,value ="registTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date registTimeFirst ,
        HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,user_name,user_password,real_name,score_amount,money_amount,regist_time,last_login_time,account_status,sex,birthday,head_img_src,account_level,mobile,nickname,two_code_img_src,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        pageSize = Math.min(UserWebConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "userNameFirst",userNameFirst ,
                "registTimeFirst",registTimeFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.userWebService.getUserWebList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.userWebService.getUserWebListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toWebSuccessJson(response, ajaxData);
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<UserWeb> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引

            //使用索引替换外键展示值
            for(UserWeb item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                obj.put("registTime",item.getRegistTime());
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
        UserWeb entity = this.userWebService.selectOneUserWebWillThrowException(reqMap);
        model.addAttribute("userWeb", entity);
        foreignModel(entity,model);
        return "admin/userweb/userWebDetail";
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail( HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
            return "admin/priorityAlert";
        }
        model.addAttribute("userWeb", new UserWeb());
        return "admin/userweb/userWebDetail";
    }

    private void foreignModel(UserWeb entity,ModelMap model){
        Map<String,Object> map = null;
    }

    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
        public String detailId(@PathVariable Long id,
        HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        UserWeb entity = this.userWebService.getUserWeb(id);
        model.addAttribute("userWeb", entity);
        foreignModel(entity,model);
        return "admin/userweb/userWebDetail";
    }

    //ajax更新状态
    @RequestMapping(value="ajax_update", method={RequestMethod.POST})
    public void ajaxUpdate(@ModelAttribute UserWeb userWeb,
        HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        try{
            this.userWebService.updateUserWebWithoutNull(userWeb);
            toWebSuccessJson(response);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            toWebFailureJson(response,exceptionMsg);
        }
    }
    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute UserWeb userWeb,
        HttpServletRequest request,ModelMap model) {
        try{
            model.addAttribute("userWeb",userWeb);
            foreignModel(userWeb,model);
            Date now = new Date();
            if (userWeb.getId() == null) {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
                    return "admin/priorityAlert";
                }
                this.userWebService.insertUserWeb(userWeb);
            } else {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
                    return "admin/priorityAlert";
                }
                UserWeb entity = this.userWebService.getUserWeb(userWeb.getId());
                Copyer.copy(userWeb, entity);
                this.userWebService.updateUserWeb(entity);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/userweb/userWebDetail";
        }
        return "redirect:/admin/user_web/list";
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
        this.userWebService.deleteUserWeb(id);
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
                this.userWebService.batchDeleteUserWeb(list);
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
        this.userWebService.truncateUserWeb();
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
            List<UserWeb> list = SerializerFastJsonUtil.get().readJsonList(fileContent,UserWeb.class);
            if(ListUtils.isNotBlank(list)){
                this.userWebService.transactionImportJsonList(list);
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
        @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
        @RequestParam(required = false,value ="registTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date registTimeFirst ,
        HttpServletRequest request,HttpServletResponse response){
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanExport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,user_name,user_password,real_name,score_amount,money_amount,regist_time,last_login_time,account_status,sex,birthday,head_img_src,account_level,mobile,nickname,two_code_img_src,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "userNameFirst",userNameFirst ,
                "registTimeFirst",registTimeFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.userWebService.getUserWebList(query);

        String file = "user_web";
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
        List<UserWeb> list = userWebService.tryQueryList(keyword,selectValue,foreignJavaField);
        toSimpleJson(response,ProjectUtil.showList(list,selectValue,foreignJavaField));
    }
    //===================end=================================

}

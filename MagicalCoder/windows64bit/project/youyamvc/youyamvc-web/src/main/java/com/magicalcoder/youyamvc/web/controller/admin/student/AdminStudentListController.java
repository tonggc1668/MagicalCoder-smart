package com.magicalcoder.youyamvc.web.controller.admin.student;
import com.magicalcoder.youyamvc.app.student.service.StudentService;
import com.magicalcoder.youyamvc.app.student.service.StudentProxyService;
import com.magicalcoder.youyamvc.app.student.constant.StudentConstant;
import com.magicalcoder.youyamvc.app.student.util.StudentUtil;
import com.magicalcoder.youyamvc.app.student.dto.StudentDto;
import com.magicalcoder.youyamvc.app.model.Student;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.common.dto.InputSelectShowDto;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserContextUtil;
import com.magicalcoder.youyamvc.app.model.Classes;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
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

/*{*//*}*//**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/admin/student"})
@Controller
public class AdminStudentListController extends AdminLoginController
{
    /*{*/
    private void test(){

    }
    /*}*/private final String moduleName = "student";
    /*{*/
    private void test1(){

    }
    /*}*/
    @Resource
    private ClassesService classesService;
    /*{*/
    private void test2(){

    }
    /*}*/
    @Resource
    private AdminUserService adminUserService;
    /*{*/
    private void test3(){

    }
    /*}*/
    @Resource
    private StudentService studentService;
    @Resource
    private StudentProxyService studentProxyService;
    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        List<Classes> classesList =
                    classesService.getClassesList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("classesList", classesList);
        List<AdminUser> adminUserList =
                    adminUserService.getAdminUserList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("adminUserList", /*{*/adminUserList/*}*/);

        return "admin/student/studentList";
    }

    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="nameFirst")                        String nameFirst ,
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="sexFirst")                        Integer sexFirst ,
        @RequestParam(required = false,value ="adminUserIdFirst")                        Long adminUserIdFirst ,
        HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",name,class_id,sex,id,admin_user_id,create_time,update_time,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        if(StringUtils.isBlank(orderBy)){
            orderBy = "class_id desc";
        }
        pageSize = Math.min(StudentConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "nameFirst",nameFirst ,
                "classIdFirst",classIdFirst ,
                "sexFirst",sexFirst ,
                "adminUserIdFirst",adminUserIdFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.studentService.getStudentList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.studentService.getStudentListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toWebSuccessJson(response, ajaxData);
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<Student> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理classId外键
            StringBuffer classIds = new StringBuffer();
            for(Student item : pageList){
                if(!classIds.toString().contains(","+item.getClassId()+",")){
                         classIds.append(item.getClassId()).append(",");
                }
            }

            List<Classes> classesList = classesService.getClassesList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(classIds.toString())+")"));
            Map<Long,Classes> classesMap = new HashMap<Long, Classes>();
            if(ListUtils.isNotBlank(classesList)){
                for(Classes entity:classesList){
                    classesMap.put(entity.getId(),entity);
                }
            }
            //处理adminUserId外键
            StringBuffer adminUserIds = new StringBuffer();
            for(Student item : pageList){
                if(!adminUserIds.toString().contains(","+item.getAdminUserId()+",")){
                         adminUserIds.append(item.getAdminUserId()).append(",");
                }
            }

            List<AdminUser> adminUserList = adminUserService.getAdminUserList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(adminUserIds.toString())+")"));
            Map<Long,AdminUser> adminUserMap = new HashMap<Long, AdminUser>();
            if(ListUtils.isNotBlank(adminUserList)){
                for(AdminUser entity:adminUserList){
                    adminUserMap.put(entity.getId(),entity);
                }
            }

            //使用索引替换外键展示值
            for(Student item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                Long classId = item.getClassId();
                Classes classes = classesMap.get(classId);
                String classIdForeignShowValue = classes==null?"":""+classes.getClassName();
                obj.put("classIdForeignShowValue",classIdForeignShowValue);
                Long adminUserId = item.getAdminUserId();
                AdminUser adminUser = adminUserMap.get(adminUserId);
                String adminUserIdForeignShowValue = adminUser==null?"":""+adminUser.getUserName()+"-"+adminUser.getRealName();
                obj.put("adminUserIdForeignShowValue",adminUserIdForeignShowValue);
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
        Student entity = this.studentService.selectOneStudentWillThrowException(reqMap);
        model.addAttribute("student", entity);
        foreignModel(entity,model);
        return "admin/student/studentDetail";
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail( HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
            return "admin/priorityAlert";
        }
        model.addAttribute("student", new Student());
        return "admin/student/studentDetail";
    }

    private void foreignModel(Student entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getClassId());
                Classes classes= classesService.selectOneClassesWillThrowException(map);
            model.addAttribute("classes",classes);
            map = ProjectUtil.buildMap("id",entity.getAdminUserId());
                AdminUser adminUser= adminUserService.selectOneAdminUserWillThrowException(map);
            model.addAttribute("adminUser",adminUser);
    }

    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
        public String detailId(@PathVariable Integer id,
        HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        Student entity = this.studentService.getStudent(id);
        model.addAttribute("student", entity);
        foreignModel(entity,model);
        return "admin/student/studentDetail";
    }

    //ajax更新状态
    @RequestMapping(value="ajax_update", method={RequestMethod.POST})
    public void ajaxUpdate(@ModelAttribute Student student,
        HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        try{
            this.studentService.updateStudentWithoutNull(student);
            toWebSuccessJson(response);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            toWebFailureJson(response,exceptionMsg);
        }
    }
    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute Student student,
        HttpServletRequest request,ModelMap model) {
        try{
        Long adminUserId = AdminUserContextUtil.getLoginUserDto(request).getId();
            student.setAdminUserId(adminUserId);
            model.addAttribute("student",student);
            foreignModel(student,model);
            Date now = new Date();
                    student.setUpdateTime(now);
            if (student.getId() == null) {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
                    return "admin/priorityAlert";
                }
                        student.setCreateTime(now);
                this.studentService.insertStudent(student);
            } else {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
                    return "admin/priorityAlert";
                }
                Student entity = this.studentService.getStudent(student.getId());
                        student.setCreateTime(entity.getCreateTime());
                Copyer.copy(student, entity);
                this.studentService.updateStudent(entity);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/student/studentDetail";
        }
        return "redirect:/admin/student/list";
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Integer id,
        HttpServletRequest request, HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanDelete()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        this.studentService.deleteStudent(id);
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
            List<Integer> list = new ArrayList<Integer>();
            for(String id:idArr){
                if(StringUtils.isNotBlank(id)){
                    list.add(Integer.valueOf(id));
                }
            }
            if(ListUtils.isNotBlank(list)){
                this.studentService.batchDeleteStudent(list);
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
        this.studentService.truncateStudent();
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
            List<Student> list = SerializerFastJsonUtil.get().readJsonList(fileContent,Student.class);
            if(ListUtils.isNotBlank(list)){
                this.studentService.transactionImportJsonList(list);
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
        @RequestParam(required = false,value ="nameFirst")                        String nameFirst ,
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="sexFirst")                        Integer sexFirst ,
        @RequestParam(required = false,value ="adminUserIdFirst")                        Long adminUserIdFirst ,
        HttpServletRequest request,HttpServletResponse response){
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanExport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",name,class_id,sex,id,admin_user_id,create_time,update_time,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "nameFirst",nameFirst ,
                "classIdFirst",classIdFirst ,
                "sexFirst",sexFirst ,
                "adminUserIdFirst",adminUserIdFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.studentService.getStudentList(query);

        String file = "student";
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
        List<Student> list = studentService.tryQueryList(keyword,selectValue,foreignJavaField);
        toSimpleJson(response,ProjectUtil.showList(list,selectValue,foreignJavaField));
    }
    //===================end=================================

}

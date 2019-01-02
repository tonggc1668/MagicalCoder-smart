package com.magicalcoder.youyamvc.web.controller.admin.classteacher;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherService;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherProxyService;
import com.magicalcoder.youyamvc.app.classteacher.constant.ClassTeacherConstant;
import com.magicalcoder.youyamvc.app.classteacher.util.ClassTeacherUtil;
import com.magicalcoder.youyamvc.app.classteacher.dto.ClassTeacherDto;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
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
import com.magicalcoder.youyamvc.app.model.Teacher;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherService;
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
@RequestMapping({"/admin/class_teacher"})
@Controller
public class AdminClassTeacherListController extends AdminLoginController
{
    private final String moduleName = "class_teacher";
    @Resource
    private ClassesService classesService;
    @Resource
    private TeacherService teacherService;

    @Resource
    private ClassTeacherService classTeacherService;
    @Resource
    private ClassTeacherProxyService classTeacherProxyService;
    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        return "admin/classteacher/classTeacherList";
    }

    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="teacherIdFirst")                        Long teacherIdFirst ,
        HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,class_id,teacher_id,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        pageSize = Math.min(ClassTeacherConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classIdFirst",classIdFirst ,
                "teacherIdFirst",teacherIdFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.classTeacherService.getClassTeacherList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.classTeacherService.getClassTeacherListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toWebSuccessJson(response, ajaxData);
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<ClassTeacher> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理classId外键
            StringBuffer classIds = new StringBuffer();
            for(ClassTeacher item : pageList){
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
            //处理teacherId外键
            StringBuffer teacherIds = new StringBuffer();
            for(ClassTeacher item : pageList){
                if(!teacherIds.toString().contains(","+item.getTeacherId()+",")){
                         teacherIds.append(item.getTeacherId()).append(",");
                }
            }

            List<Teacher> teacherList = teacherService.getTeacherList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(teacherIds.toString())+")"));
            Map<Long,Teacher> teacherMap = new HashMap<Long, Teacher>();
            if(ListUtils.isNotBlank(teacherList)){
                for(Teacher entity:teacherList){
                    teacherMap.put(entity.getId(),entity);
                }
            }

            //使用索引替换外键展示值
            for(ClassTeacher item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                Long classId = item.getClassId();
                Classes classes = classesMap.get(classId);
                String classIdForeignShowValue = classes==null?"":""+classes.getClassName();
                obj.put("classIdForeignShowValue",classIdForeignShowValue);
                Long teacherId = item.getTeacherId();
                Teacher teacher = teacherMap.get(teacherId);
                String teacherIdForeignShowValue = teacher==null?"":""+teacher.getTeacherName();
                obj.put("teacherIdForeignShowValue",teacherIdForeignShowValue);
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
        ClassTeacher entity = this.classTeacherService.selectOneClassTeacherWillThrowException(reqMap);
        model.addAttribute("classTeacher", entity);
        foreignModel(entity,model);
        return "admin/classteacher/classTeacherDetail";
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail( HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
            return "admin/priorityAlert";
        }
        model.addAttribute("classTeacher", new ClassTeacher());
        return "admin/classteacher/classTeacherDetail";
    }

    private void foreignModel(ClassTeacher entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getClassId());
                Classes classes= classesService.selectOneClassesWillThrowException(map);
            model.addAttribute("classes",classes);
            map = ProjectUtil.buildMap("id",entity.getTeacherId());
                Teacher teacher= teacherService.selectOneTeacherWillThrowException(map);
            model.addAttribute("teacher",teacher);
    }

    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
        public String detailId(@PathVariable Long id,
        HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        ClassTeacher entity = this.classTeacherService.getClassTeacher(id);
        model.addAttribute("classTeacher", entity);
        foreignModel(entity,model);
        return "admin/classteacher/classTeacherDetail";
    }

    //ajax更新状态
    @RequestMapping(value="ajax_update", method={RequestMethod.POST})
    public void ajaxUpdate(@ModelAttribute ClassTeacher classTeacher,
        HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        try{
            this.classTeacherService.updateClassTeacherWithoutNull(classTeacher);
            toWebSuccessJson(response);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            toWebFailureJson(response,exceptionMsg);
        }
    }
    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute ClassTeacher classTeacher,
        HttpServletRequest request,ModelMap model) {
        try{
            model.addAttribute("classTeacher",classTeacher);
            foreignModel(classTeacher,model);
            Date now = new Date();
            if (classTeacher.getId() == null) {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
                    return "admin/priorityAlert";
                }
                this.classTeacherService.insertClassTeacher(classTeacher);
            } else {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
                    return "admin/priorityAlert";
                }
                ClassTeacher entity = this.classTeacherService.getClassTeacher(classTeacher.getId());
                Copyer.copy(classTeacher, entity);
                this.classTeacherService.updateClassTeacher(entity);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/classteacher/classTeacherDetail";
        }
        return "redirect:/admin/class_teacher/list";
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
        this.classTeacherService.deleteClassTeacher(id);
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
                this.classTeacherService.batchDeleteClassTeacher(list);
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
        this.classTeacherService.truncateClassTeacher();
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
            List<ClassTeacher> list = SerializerFastJsonUtil.get().readJsonList(fileContent,ClassTeacher.class);
            if(ListUtils.isNotBlank(list)){
                this.classTeacherService.transactionImportJsonList(list);
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
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="teacherIdFirst")                        Long teacherIdFirst ,
        HttpServletRequest request,HttpServletResponse response){
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanExport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,class_id,teacher_id,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classIdFirst",classIdFirst ,
                "teacherIdFirst",teacherIdFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.classTeacherService.getClassTeacherList(query);

        String file = "class_teacher";
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

    @RequestMapping(value = "relate/{classId}")
    public String relate(@PathVariable Long classId,ModelMap modelMap){
        modelMap.addAttribute("classId",classId);
        return "admin/classteacher/relate";
    }

    //详情页的关联分页功能
    @RequestMapping(value={"page_relate/{pageIndex}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex, @PathVariable Integer pageCount,
    @RequestParam(required=false, value="orderBy") String orderBy,
                @RequestParam(required = false,value ="teacherNameFirst")
                        String teacherNameFirst ,
                @RequestParam(required = false,value ="ageFirst")
                        Integer ageFirst ,
    @RequestParam(required = false,value ="classId") Long classId ,
    HttpServletResponse response)
    {
        if(StringUtils.isBlank(orderBy)){
            orderBy = "classTeacher.teacher_id desc";
        }else{
            orderBy += ",classTeacher.teacher_id desc";
        }
        int idx = (pageIndex.intValue() - 1) * 20;
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "teacherNameFirst",teacherNameFirst ,
                "ageFirst",ageFirst ,
        "limitIndex",Integer.valueOf(idx),"limit", Integer.valueOf(20) });
        query.put("classId",classId );

        List    pageList = this.classTeacherService.getManyList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
            pageCount = this.classTeacherService.getManyListCount(query);
        }
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }

    //添加关联
    @RequestMapping(value={"/add_relate/{classId}/{teacherId}"}, method={RequestMethod.GET})
    public void addRelate(@PathVariable Long classId,
    @PathVariable Long teacherId,
    HttpServletResponse response) {
        ClassTeacher entity = new ClassTeacher();
        entity.setClassId(classId);
        entity.setTeacherId(teacherId);
        classTeacherService.insertClassTeacher(entity);
        toJson(response, new AjaxData("ok", "", ""));
    }

    //删除关联
    @RequestMapping(value={"/delete_relate/{classId}/{teacherId}"}, method={RequestMethod.GET})
    public void deleteRelate(@PathVariable Long classId,
    @PathVariable Long teacherId,
    HttpServletResponse response) {
        StringBuffer whereSql = new StringBuffer();
        whereSql.append("class_id=").append(classId).append(" and teacher_id=").append(teacherId);
        this.classTeacherService.deleteClassTeacherByWhereSql(whereSql.toString());
        toJson(response, new AjaxData("ok", "", ""));
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
        List<ClassTeacher> list = classTeacherService.tryQueryList(keyword,selectValue,foreignJavaField);
        toSimpleJson(response,ProjectUtil.showList(list,selectValue,foreignJavaField));
    }
    //===================end=================================

}

package com.magicalcoder.youyamvc.web.controller.web.student;
import com.magicalcoder.youyamvc.app.student.service.StudentService;
import com.magicalcoder.youyamvc.app.student.service.StudentProxyService;
import com.magicalcoder.youyamvc.app.student.constant.StudentConstant;
import com.magicalcoder.youyamvc.app.student.util.StudentUtil;
import com.magicalcoder.youyamvc.app.model.Student;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.spring.web.WebLoginController;
import com.magicalcoder.youyamvc.web.common.BaseController;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;
import java.math.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/web/student"})
@Controller
public class StudentListController extends BaseController
{
    @Resource
    private StudentService studentService;
    @Resource
    private StudentProxyService studentProxyService;

    /**  method:GET
    *   url:/web/student/list/{pageIndex}/{pageSize}?...
    *   demo:/web/student/list/1/20
    *   获取学生分页数据
    *   是否需要登录
    *是否必须   入参                          注释
    *  是   @pageIndex                     (Integer)当前页码 [1,n]
    *  是   @pageSize                      (Integer)每页条数 [1,n]
    *  否   @pageCount                     (Integer)页数 如果传进来将会优化性能 对于需要分页的数据 请求第二页的时候可以把上一页的值传进来
    *  否   @needPageCount                 (Boolean)是否需要返回pageCount
    *  否   @callback                      (String)回调方法
    *  否   @orderBy                       (String)排序字段
    *  否   @encode                        (String)编码 默认UTF-8
    *  否   @modelView                     (String)模板方法 vm|json
    *  否   @name                     (String)学生名称 
    *  否   @classId                     (Long)所属班级 
    *  否   @sex                     (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *  否   @id                     (Integer)主键 
    *  否   @adminUserId                     (Long)管理员 
    *  否   @createTime                     (Date)创建时间 
    *  否   @updateTime                     (Date)更新时间 
    *  否   @nameFirst                     (String)学生名称 
    *  否   @classIdFirst                     (Long)所属班级 
    *  否   @sexFirst                     (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *  否   @adminUserIdFirst                     (Long)管理员 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              name         (String)学生名称 
    *              classId         (Long)所属班级 
    *              sex         (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              id         (Integer)主键 
    *              adminUserId         (Long)管理员 
    *              createTime         (Date)创建时间 
    *              updateTime         (Date)更新时间 
    *      }]
    *   }
    */
    @RequestMapping(value={"list/{pageIndex}/{pageSize}"}, method={RequestMethod.GET})
    public String list(@PathVariable Integer pageIndex,@PathVariable Integer pageSize,
        @RequestParam(required=false, value="orderBy") String orderBy,
        @RequestParam(required=false, value="pageCount") Integer pageCount,
        @RequestParam(required=false, value="needPageCount") Boolean needPageCount,
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="name")   String name,
        @RequestParam(required = false,value ="classId")   Long classId,
        @RequestParam(required = false,value ="sex")   Integer sex,
        @RequestParam(required = false,value ="id")   Integer id,
        @RequestParam(required = false,value ="adminUserId")   Long adminUserId,
        @RequestParam(required = false,value ="createTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTime,
        @RequestParam(required = false,value ="updateTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTime,
        @RequestParam(required = false,value ="nameFirst")                        String nameFirst ,
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="sexFirst")                        Integer sexFirst ,
        @RequestParam(required = false,value ="adminUserIdFirst")                        Long adminUserIdFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",name,class_id,sex,id,admin_user_id,create_time,update_time,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(StudentConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "name",name,
         "classId",classId,
         "sex",sex,
         "id",id,
         "adminUserId",adminUserId,
         "createTime",createTime,
         "updateTime",updateTime,
                "nameFirst",nameFirst ,
                "classIdFirst",classIdFirst ,
                "sexFirst",sexFirst ,
                "adminUserIdFirst",adminUserIdFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Student> pageList = this.studentService.getStudentList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.studentService.getStudentListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("studentService",studentService);
            modelMap.addAttribute("studentProxyService",studentProxyService);
            return "web/student/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(Student entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",entity.getName());
        map.put("classId",entity.getClassId());
        map.put("sex",entity.getSex());
        map.put("id",entity.getId());
        map.put("adminUserId",entity.getAdminUserId());
        map.put("createTime",entity.getCreateTime());
        map.put("updateTime",entity.getUpdateTime());
        return map;
    }*/
    /** method:GET
    *   url:/web/student/item?...
    *   demo:/web/student/item
    *   获取学生分页数据
    *   是否需要登录
    *是否必须   入参                          注释
    *  否   @callback                      (String)回调方法
    *  否   @orderBy                       (String)排序字段
    *  否   @encode                        (String)编码 默认UTF-8
    *  否   @modelView                     (String)模板方法 vm|json
     *  否  @id        (Integer) 主键
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                {
    *              name         (String)学生名称 
    *              classId         (Long)所属班级 
    *              sex         (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              id         (Integer)主键 
    *              adminUserId         (Long)管理员 
    *              createTime         (Date)创建时间 
    *              updateTime         (Date)更新时间 
    *      }
    *   }
    */
    @RequestMapping(value={"item"}, method={RequestMethod.GET})
    public String item(
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="modelView") String modelView ,
        @RequestParam(required = false,value ="id")Integer id,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
     Long userId = UserWebUtil.userId(request);
     Map<String,Object> query = ProjectUtil.buildMap(
                 "id",id,
            "limitIndex",0,"limit", 1 ,"userId",userId
        );

        List<Student> pageList = this.studentService.getStudentList(query);
        Student student = null;
        if(pageList!=null && pageList.size()>0){
            student = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("student",student);
            modelMap.addAttribute("studentService",studentService);
            modelMap.addAttribute("studentProxyService",studentProxyService);
            return "web/student/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,student);
            return null;
        }
    }

    /** method:get
    *   url:/web/student/hi/{id}/{pageIndex}/{pageSize}?...
    *   demo:/web/student/hi/{id}/{pageIndex}/{pageSize}
    *   是否需要登录
    *是否必须   入参                          注释
    *  否   @pageCount                     (Integer)页数 如果传进来将会优化性能 对于需要分页的数据 请求第二页的时候可以把上一页的值传进来
    *  否   @needPageCount                 (Boolean)是否需要返回pageCount
    *  否   @callback                      (String)回调方法
    *  否   @orderBy                       (String)排序字段
    *  否   @encode                        (String)编码 默认UTF-8
    *  否   @modelView                     (String)模板方法 vm|json
    *  是   @id     (Integer)主键
    *  是   @pageIndex                     (Integer)当前页码 [1,n]
    *  是   @pageSize                      (Integer)每页条数 [1,n]
        @PathVariable Integer pageSize,
       是   id        (Integer) 主键
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
               pageCount://总数目
               pageList:[{
        *              name         (String)学生名称 
        *              classId         (Long)所属班级 
        *              sex         (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
        *              id         (Integer)主键 
        *              adminUserId         (Long)管理员 
        *              createTime         (Date)创建时间 
        *              updateTime         (Date)更新时间 
    *          }]
    *   }
    */
    @RequestMapping(value={"hi/{id}/{pageIndex}/{pageSize}"}  ,method={RequestMethod.GET})
    public String test(
        HttpServletRequest request,HttpServletResponse response,
        @PathVariable Integer id,
        @PathVariable Integer pageIndex,
        @PathVariable Integer pageSize,

        @RequestParam(required=false, value="orderBy") String orderBy,
        @RequestParam(required=false, value="pageCount") Integer pageCount,
        @RequestParam(required=false, value="needPageCount") Boolean needPageCount,
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="modelView") String modelView ,
        ModelMap modelMap
        ){
            String orderBySqlFieldStr = ",name,class_id,sex,id,admin_user_id,create_time,update_time,";
            orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
            Long userId = UserWebUtil.userId(request);
            Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "id" ,id,
            "userId",userId});
            if(pageSize!=null && pageIndex!=null){
                pageSize = Math.min(StudentConstant.PAGE_MAX_SIZE,pageSize);
                int limitIndex = (pageIndex.intValue() - 1) * pageSize;
                query.put("limitIndex",limitIndex);
                query.put("limit", pageSize);
            }

            List<Student> pageList = this.studentService.getStudentList(query);
            Map ajaxData = new HashMap();
            ajaxData.put("pageList", pageList);
            if(needPageCount!=null && needPageCount){
                if (pageCount == null || pageCount.intValue() == 0) {
                    query.remove("orderBy");
                    query.remove("limitIndex");
                    query.remove("limit");
                    pageCount = this.studentService.getStudentListCount(query);
                }
                ajaxData.put("pageCount", pageCount);
            }
        if("vm".equals(modelView)){
            modelMap.addAttribute("id",id);
            modelMap.addAttribute("pageIndex",pageIndex);
            modelMap.addAttribute("pageSize",pageSize);
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("studentService",studentService);
            modelMap.addAttribute("studentProxyService",studentProxyService);
            return "web/student/hi.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }    /** method:get
    *   url:/web/student/tell/{name}/{myName}?...
    *   demo:/web/student/tell/{name}/{myName}
    *   是否需要登录
    *是否必须   入参                          注释
    *  否   @pageCount                     (Integer)页数 如果传进来将会优化性能 对于需要分页的数据 请求第二页的时候可以把上一页的值传进来
    *  否   @needPageCount                 (Boolean)是否需要返回pageCount
    *  否   @callback                      (String)回调方法
    *  否   @orderBy                       (String)排序字段
    *  否   @encode                        (String)编码 默认UTF-8
    *  否   @modelView                     (String)模板方法 vm|json
    *  是   @name     (String)学生名称
    *  是   @myName                      (String)myName
       是   name        (String) 学生名称
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
               pageCount://总数目
               pageList:[{
        *              name         (String)学生名称 
        *              classId         (Long)所属班级 
        *              sex         (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
        *              id         (Integer)主键 
        *              adminUserId         (Long)管理员 
        *              createTime         (Date)创建时间 
        *              updateTime         (Date)更新时间 
    *          }]
    *   }
    */
    @RequestMapping(value={"tell/{name}/{myName}"}  ,method={RequestMethod.GET})
    public String tell(
        HttpServletRequest request,HttpServletResponse response,
        @PathVariable String name,
        @PathVariable String myName,

        @RequestParam(required=false, value="orderBy") String orderBy,
        @RequestParam(required=false, value="pageCount") Integer pageCount,
        @RequestParam(required=false, value="needPageCount") Boolean needPageCount,
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="modelView") String modelView ,
        ModelMap modelMap
        ){
            String orderBySqlFieldStr = ",name,class_id,sex,id,admin_user_id,create_time,update_time,";
            orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
            Long userId = UserWebUtil.userId(request);
            Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "name" ,name,
            "userId",userId});

            List<Student> pageList = this.studentService.getStudentList(query);
            Map ajaxData = new HashMap();
            ajaxData.put("pageList", pageList);
            if(needPageCount!=null && needPageCount){
                if (pageCount == null || pageCount.intValue() == 0) {
                    query.remove("orderBy");
                    query.remove("limitIndex");
                    query.remove("limit");
                    pageCount = this.studentService.getStudentListCount(query);
                }
                ajaxData.put("pageCount", pageCount);
            }
        if("vm".equals(modelView)){
            modelMap.addAttribute("name",name);
            modelMap.addAttribute("myName",myName);
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("studentService",studentService);
            modelMap.addAttribute("studentProxyService",studentProxyService);
            return "web/student/tell.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }    /** method:
    *   url:/web/student/save?...
    *   demo:/web/student/save
    *   保存学生
    *是否必须         入参         注释
    * 否name (String)学生名称  
    * 否classId (Long)所属班级  
    * 是sex (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}] 
    * 否id (Integer)主键  为空表示插入否则表示更新
    * 否adminUserId (Long)管理员  
    * 否createTime (Date)创建时间  
    * 否updateTime (Date)更新时间  
    *
    *   返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:id 主键 Integer
    *   }
    */
    @RequestMapping(value={"save"} )
    public String save(@ModelAttribute Student student,
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="modelView") String modelView ,
        ModelMap modelMap,
        HttpServletRequest request,HttpServletResponse response) {
        Long userId = UserWebUtil.userId(request);
        try{
            Integer id = saveEntity(userId,student);
            if("vm".equals(modelView)){
                modelMap.addAttribute("studentService",studentService);
                modelMap.addAttribute("studentProxyService",studentProxyService);
                return "web/student/save.vm";
            }else {
                toWebSuccessJson(response, callback,encode,id);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            toWebFailureJson(response, callback,encode,"保存失败："+exceptionMsg);
        }
        return null;
    }

    private Integer saveEntity(Long userId,Student student){

        if (student.getId() == null) {
            return this.studentService.insertStudent(student);
        } else {
            Student entity = getEntity(userId,student.getId());
            if(entity!=null){
                CopyerSpringUtil.copyProperties(student, entity);
                this.studentService.updateStudent(entity);
            }
        }
        return student.getId();
    }

    private Student getEntity(Long userId,Integer id){
        return this.studentService.selectOneStudentWillThrowException(ProjectUtil.buildMap(
            "id",id));
    }

    /** method:get
    *   url:/web/student/delete/{id}/{name}/{my}?...
    *   demo:/web/student/delete/{id}/{name}/{my}
    *   根据主键删除学生
    *是否必须         入参                              注释
        *  是   @id     (Integer)主键
        *  是   @name     (String)学生名称
            *  是   @my                      (String)my
    *
    *   返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:true||false
    *   }
    */
    @RequestMapping(value = {"delete/{id}/{name}/{my}"}  ,method={RequestMethod.GET})
    public String delete(
        @PathVariable Integer id,
        @PathVariable String name,
            @PathVariable String my,
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="modelView") String modelView ,
        ModelMap modelMap,
        HttpServletRequest request,HttpServletResponse response) {
        Long userId = UserWebUtil.userId(request);
        Map<String,Object> query = ProjectUtil.buildMap(
            "name" ,name,
            "id" ,id,
        "userId",userId);
        this.studentService.deleteStudentList(query);
        if("vm".equals(modelView)){
            modelMap.addAttribute("id",id);
            modelMap.addAttribute("name",name);
            modelMap.addAttribute("my",my);
            modelMap.addAttribute("studentService",studentService);
            modelMap.addAttribute("studentProxyService",studentProxyService);
            return "web/student/delete.vm";
        }else {
            toWebSuccessJson(response, callback,encode,true);
            return null;
        }
    }

}
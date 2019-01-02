package com.magicalcoder.youyamvc.web.controller.web.classteacher;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherService;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherProxyService;
import com.magicalcoder.youyamvc.app.classteacher.constant.ClassTeacherConstant;
import com.magicalcoder.youyamvc.app.classteacher.util.ClassTeacherUtil;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
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
@RequestMapping({"/web/class_teacher"})
@Controller
public class ClassTeacherListController extends BaseController
{
    @Resource
    private ClassTeacherService classTeacherService;
    @Resource
    private ClassTeacherProxyService classTeacherProxyService;

    /**  method:GET
    *   url:/web/class_teacher/list/{pageIndex}/{pageSize}?...
    *   demo:/web/class_teacher/list/1/20
    *   获取班级关联教师分页数据
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
    *  否   @id                     (Long)班级关联老师表主键 
    *  否   @classId                     (Long)班级表主键 
    *  否   @teacherId                     (Long)老师表主键 
    *  否   @classIdFirst                     (Long)班级表主键 
    *  否   @teacherIdFirst                     (Long)老师表主键 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)班级关联老师表主键 
    *              classId         (Long)班级表主键 
    *              teacherId         (Long)老师表主键 
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
        @RequestParam(required = false,value ="id")   Long id,
        @RequestParam(required = false,value ="classId")   Long classId,
        @RequestParam(required = false,value ="teacherId")   Long teacherId,
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="teacherIdFirst")                        Long teacherIdFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,class_id,teacher_id,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(ClassTeacherConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "classId",classId,
         "teacherId",teacherId,
                "classIdFirst",classIdFirst ,
                "teacherIdFirst",teacherIdFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<ClassTeacher> pageList = this.classTeacherService.getClassTeacherList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.classTeacherService.getClassTeacherListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("classTeacherService",classTeacherService);
            modelMap.addAttribute("classTeacherProxyService",classTeacherProxyService);
            return "web/classteacher/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(ClassTeacher entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("classId",entity.getClassId());
        map.put("teacherId",entity.getTeacherId());
        return map;
    }*/
    /** method:GET
    *   url:/web/class_teacher/item?...
    *   demo:/web/class_teacher/item
    *   获取班级关联教师分页数据
    *   是否需要登录
    *是否必须   入参                          注释
    *  否   @callback                      (String)回调方法
    *  否   @orderBy                       (String)排序字段
    *  否   @encode                        (String)编码 默认UTF-8
    *  否   @modelView                     (String)模板方法 vm|json
     *  否  @id        (Long) 主键
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                {
    *              id         (Long)班级关联老师表主键 
    *              classId         (Long)班级表主键 
    *              teacherId         (Long)老师表主键 
    *      }
    *   }
    */
    @RequestMapping(value={"item"}, method={RequestMethod.GET})
    public String item(
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="modelView") String modelView ,
        @RequestParam(required = false,value ="id")Long id,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
     Long userId = UserWebUtil.userId(request);
     Map<String,Object> query = ProjectUtil.buildMap(
                 "id",id,
            "limitIndex",0,"limit", 1 ,"userId",userId
        );

        List<ClassTeacher> pageList = this.classTeacherService.getClassTeacherList(query);
        ClassTeacher classTeacher = null;
        if(pageList!=null && pageList.size()>0){
            classTeacher = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("classTeacher",classTeacher);
            modelMap.addAttribute("classTeacherService",classTeacherService);
            modelMap.addAttribute("classTeacherProxyService",classTeacherProxyService);
            return "web/classteacher/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,classTeacher);
            return null;
        }
    }



}
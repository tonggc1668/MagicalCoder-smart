package com.magicalcoder.youyamvc.web.controller.web.teacher;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherService;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherProxyService;
import com.magicalcoder.youyamvc.app.teacher.constant.TeacherConstant;
import com.magicalcoder.youyamvc.app.teacher.util.TeacherUtil;
import com.magicalcoder.youyamvc.app.model.Teacher;
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
@RequestMapping({"/web/teacher"})
@Controller
public class TeacherListController extends BaseController
{
    @Resource
    private TeacherService teacherService;
    @Resource
    private TeacherProxyService teacherProxyService;

    /**  method:GET
    *   url:/web/teacher/list/{pageIndex}/{pageSize}?...
    *   demo:/web/teacher/list/1/20
    *   获取教师分页数据
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
    *  否   @id                     (Long)老师主键 
    *  否   @teacherName                     (String)老师名称 
    *  否   @age                     (Integer)老师年龄 
    *  否   @teacherNameFirst                     (String)老师名称 
    *  否   @ageFirst                     (Integer)老师年龄 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)老师主键 
    *              teacherName         (String)老师名称 
    *              age         (Integer)老师年龄 
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
        @RequestParam(required = false,value ="teacherName")   String teacherName,
        @RequestParam(required = false,value ="age")   Integer age,
        @RequestParam(required = false,value ="teacherNameFirst")                        String teacherNameFirst ,
        @RequestParam(required = false,value ="ageFirst")                        Integer ageFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,teacher_name,age,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(TeacherConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "teacherName",teacherName,
         "age",age,
                "teacherNameFirst",teacherNameFirst ,
                "ageFirst",ageFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Teacher> pageList = this.teacherService.getTeacherList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.teacherService.getTeacherListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("teacherService",teacherService);
            modelMap.addAttribute("teacherProxyService",teacherProxyService);
            return "web/teacher/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(Teacher entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("teacherName",entity.getTeacherName());
        map.put("age",entity.getAge());
        return map;
    }*/
    /** method:GET
    *   url:/web/teacher/item?...
    *   demo:/web/teacher/item
    *   获取教师分页数据
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
    *              id         (Long)老师主键 
    *              teacherName         (String)老师名称 
    *              age         (Integer)老师年龄 
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

        List<Teacher> pageList = this.teacherService.getTeacherList(query);
        Teacher teacher = null;
        if(pageList!=null && pageList.size()>0){
            teacher = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("teacher",teacher);
            modelMap.addAttribute("teacherService",teacherService);
            modelMap.addAttribute("teacherProxyService",teacherProxyService);
            return "web/teacher/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,teacher);
            return null;
        }
    }



}
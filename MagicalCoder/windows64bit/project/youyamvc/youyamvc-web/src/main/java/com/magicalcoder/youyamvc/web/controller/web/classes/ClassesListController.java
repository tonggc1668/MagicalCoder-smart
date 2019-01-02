package com.magicalcoder.youyamvc.web.controller.web.classes;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.classes.service.ClassesProxyService;
import com.magicalcoder.youyamvc.app.classes.constant.ClassesConstant;
import com.magicalcoder.youyamvc.app.classes.util.ClassesUtil;
import com.magicalcoder.youyamvc.app.model.Classes;
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
@RequestMapping({"/web/classes"})
@Controller
public class ClassesListController extends BaseController
{
    @Resource
    private ClassesService classesService;
    @Resource
    private ClassesProxyService classesProxyService;

    /**  method:GET
    *   url:/web/classes/list/{pageIndex}/{pageSize}?...
    *   demo:/web/classes/list/1/20
    *   获取班级分页数据
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
    *  否   @id                     (Long)班级主键 
    *  否   @className                     (String)班级名称 
    *  否   @studentCount                     (Integer)班级学生人数 
    *  否   @schoolId                     (Long)学校id 
    *  否   @classNameFirst                     (String)班级名称 
    *  否   @studentCountFirst                     (Integer)班级学生人数 
    *  否   @schoolIdFirst                     (Long)学校id 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)班级主键 
    *              className         (String)班级名称 
    *              studentCount         (Integer)班级学生人数 
    *              schoolId         (Long)学校id 
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
        @RequestParam(required = false,value ="className")   String className,
        @RequestParam(required = false,value ="studentCount")   Integer studentCount,
        @RequestParam(required = false,value ="schoolId")   Long schoolId,
        @RequestParam(required = false,value ="classNameFirst")                        String classNameFirst ,
        @RequestParam(required = false,value ="studentCountFirst")                        Integer studentCountFirst ,
        @RequestParam(required = false,value ="schoolIdFirst")                        Long schoolIdFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,class_name,student_count,school_id,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(ClassesConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "className",className,
         "studentCount",studentCount,
         "schoolId",schoolId,
                "classNameFirst",classNameFirst ,
                "studentCountFirst",studentCountFirst ,
                "schoolIdFirst",schoolIdFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Classes> pageList = this.classesService.getClassesList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.classesService.getClassesListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("classesService",classesService);
            modelMap.addAttribute("classesProxyService",classesProxyService);
            return "web/classes/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(Classes entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("className",entity.getClassName());
        map.put("studentCount",entity.getStudentCount());
        map.put("schoolId",entity.getSchoolId());
        return map;
    }*/
    /** method:GET
    *   url:/web/classes/item?...
    *   demo:/web/classes/item
    *   获取班级分页数据
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
    *              id         (Long)班级主键 
    *              className         (String)班级名称 
    *              studentCount         (Integer)班级学生人数 
    *              schoolId         (Long)学校id 
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

        List<Classes> pageList = this.classesService.getClassesList(query);
        Classes classes = null;
        if(pageList!=null && pageList.size()>0){
            classes = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("classes",classes);
            modelMap.addAttribute("classesService",classesService);
            modelMap.addAttribute("classesProxyService",classesProxyService);
            return "web/classes/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,classes);
            return null;
        }
    }



}
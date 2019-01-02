package com.magicalcoder.youyamvc.web.controller.web.school;
import com.magicalcoder.youyamvc.app.school.service.SchoolService;
import com.magicalcoder.youyamvc.app.school.service.SchoolProxyService;
import com.magicalcoder.youyamvc.app.school.constant.SchoolConstant;
import com.magicalcoder.youyamvc.app.school.util.SchoolUtil;
import com.magicalcoder.youyamvc.app.model.School;
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
@RequestMapping({"/web/school"})
@Controller
public class SchoolListController extends BaseController
{
    @Resource
    private SchoolService schoolService;
    @Resource
    private SchoolProxyService schoolProxyService;

    /**  method:GET
    *   url:/web/school/list/{pageIndex}/{pageSize}?...
    *   demo:/web/school/list/1/20
    *   获取学校分页数据
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
    *  否   @id                     (Long)学校主键 
    *  否   @schoolName                     (String)学校名称 
    *  否   @headImg                     (String)学校头像 
    *  否   @classCount                     (Integer)班级个数 
    *  否   @adress                     (String)学校地址 
    *  否   @schoolType                     (Integer)学校类型 [{"":"全部"},{"0":"普通"},{"1":"重点"}]
    *  否   @open                     (Boolean)是否开学 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @createTime                     (Date)创建时间 
    *  否   @schoolDesc                     (String)学校描述 
    *  否   @updateTime                     (Date)更新时间 
    *  否  like @schoolNameFirst                     (String)学校名称 
    *  否   @openFirst                     (Boolean)是否开学 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否  >= @createTimeFirst                     (Date)创建时间 
    *  否  <= @createTimeSecond                  (Date)创建时间 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)学校主键 
    *              schoolName         (String)学校名称 
    *              headImg         (String)学校头像 
    *              classCount         (Integer)班级个数 
    *              adress         (String)学校地址 
    *              schoolType         (Integer)学校类型 [{"":"全部"},{"0":"普通"},{"1":"重点"}]
    *              open         (Boolean)是否开学 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              createTime         (Date)创建时间 
    *              schoolDesc         (String)学校描述 
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
        @RequestParam(required = false,value ="id")   Long id,
        @RequestParam(required = false,value ="schoolName")   String schoolName,
        @RequestParam(required = false,value ="headImg")   String headImg,
        @RequestParam(required = false,value ="classCount")   Integer classCount,
        @RequestParam(required = false,value ="adress")   String adress,
        @RequestParam(required = false,value ="schoolType")   Integer schoolType,
        @RequestParam(required = false,value ="open")   Boolean open,
        @RequestParam(required = false,value ="createTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTime,
        @RequestParam(required = false,value ="schoolDesc")   String schoolDesc,
        @RequestParam(required = false,value ="updateTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTime,
        @RequestParam(required = false,value ="schoolNameFirst")                        String schoolNameFirst ,
        @RequestParam(required = false,value ="openFirst")                        Boolean openFirst ,
        @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeFirst ,
        @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeSecond ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,school_name,head_img,class_count,adress,school_type,open,create_time,school_desc,update_time,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(SchoolConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "schoolName",schoolName,
         "headImg",headImg,
         "classCount",classCount,
         "adress",adress,
         "schoolType",schoolType,
         "open",open,
         "createTime",createTime,
         "schoolDesc",schoolDesc,
         "updateTime",updateTime,
                "schoolNameFirst",schoolNameFirst ,
                "openFirst",openFirst ,
                "createTimeFirst",createTimeFirst ,
                "createTimeSecond",createTimeSecond ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<School> pageList = this.schoolService.getSchoolList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.schoolService.getSchoolListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("schoolService",schoolService);
            modelMap.addAttribute("schoolProxyService",schoolProxyService);
            return "web/school/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(School entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("schoolName",entity.getSchoolName());
        map.put("headImg",entity.getHeadImg());
        map.put("classCount",entity.getClassCount());
        map.put("adress",entity.getAdress());
        map.put("schoolType",entity.getSchoolType());
        map.put("open",entity.getOpen());
        map.put("createTime",entity.getCreateTime());
        map.put("schoolDesc",entity.getSchoolDesc());
        map.put("updateTime",entity.getUpdateTime());
        return map;
    }*/
    /** method:GET
    *   url:/web/school/item?...
    *   demo:/web/school/item
    *   获取学校分页数据
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
    *              id         (Long)学校主键 
    *              schoolName         (String)学校名称 
    *              headImg         (String)学校头像 
    *              classCount         (Integer)班级个数 
    *              adress         (String)学校地址 
    *              schoolType         (Integer)学校类型 [{"":"全部"},{"0":"普通"},{"1":"重点"}]
    *              open         (Boolean)是否开学 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              createTime         (Date)创建时间 
    *              schoolDesc         (String)学校描述 
    *              updateTime         (Date)更新时间 
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

        List<School> pageList = this.schoolService.getSchoolList(query);
        School school = null;
        if(pageList!=null && pageList.size()>0){
            school = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("school",school);
            modelMap.addAttribute("schoolService",schoolService);
            modelMap.addAttribute("schoolProxyService",schoolProxyService);
            return "web/school/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,school);
            return null;
        }
    }



}
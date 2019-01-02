package com.magicalcoder.youyamvc.web.controller.web.priority;
import com.magicalcoder.youyamvc.app.priority.service.PriorityService;
import com.magicalcoder.youyamvc.app.priority.service.PriorityProxyService;
import com.magicalcoder.youyamvc.app.priority.constant.PriorityConstant;
import com.magicalcoder.youyamvc.app.priority.util.PriorityUtil;
import com.magicalcoder.youyamvc.app.model.Priority;
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
@RequestMapping({"/web/priority"})
@Controller
public class PriorityListController extends BaseController
{
    @Resource
    private PriorityService priorityService;
    @Resource
    private PriorityProxyService priorityProxyService;

    /**  method:GET
    *   url:/web/priority/list/{pageIndex}/{pageSize}?...
    *   demo:/web/priority/list/1/20
    *   获取权限分页数据
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
    *  否   @id                     (Long)主键 
    *  否   @priorityName                     (String)权限名 
    *  否   @canInsert                     (Boolean)新增 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @canDelete                     (Boolean)删除 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @canUpdate                     (Boolean)编辑 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @canQuery                     (Boolean)查询 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @canTruncate                     (Boolean)清空 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @canExport                     (Boolean)导出 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否   @canImport                     (Boolean)导入 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *  否  like @priorityNameFirst                     (String)权限名 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)主键 
    *              priorityName         (String)权限名 
    *              canInsert         (Boolean)新增 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canDelete         (Boolean)删除 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canUpdate         (Boolean)编辑 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canQuery         (Boolean)查询 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canTruncate         (Boolean)清空 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canExport         (Boolean)导出 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canImport         (Boolean)导入 [{"":"全部"},{"false":"否"},{"true":"是"}]
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
        @RequestParam(required = false,value ="priorityName")   String priorityName,
        @RequestParam(required = false,value ="canInsert")   Boolean canInsert,
        @RequestParam(required = false,value ="canDelete")   Boolean canDelete,
        @RequestParam(required = false,value ="canUpdate")   Boolean canUpdate,
        @RequestParam(required = false,value ="canQuery")   Boolean canQuery,
        @RequestParam(required = false,value ="canTruncate")   Boolean canTruncate,
        @RequestParam(required = false,value ="canExport")   Boolean canExport,
        @RequestParam(required = false,value ="canImport")   Boolean canImport,
        @RequestParam(required = false,value ="priorityNameFirst")                        String priorityNameFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,priority_name,can_insert,can_delete,can_update,can_query,can_truncate,can_export,can_import,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(PriorityConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "priorityName",priorityName,
         "canInsert",canInsert,
         "canDelete",canDelete,
         "canUpdate",canUpdate,
         "canQuery",canQuery,
         "canTruncate",canTruncate,
         "canExport",canExport,
         "canImport",canImport,
                "priorityNameFirst",priorityNameFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Priority> pageList = this.priorityService.getPriorityList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.priorityService.getPriorityListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("priorityService",priorityService);
            modelMap.addAttribute("priorityProxyService",priorityProxyService);
            return "web/priority/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(Priority entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("priorityName",entity.getPriorityName());
        map.put("canInsert",entity.getCanInsert());
        map.put("canDelete",entity.getCanDelete());
        map.put("canUpdate",entity.getCanUpdate());
        map.put("canQuery",entity.getCanQuery());
        map.put("canTruncate",entity.getCanTruncate());
        map.put("canExport",entity.getCanExport());
        map.put("canImport",entity.getCanImport());
        return map;
    }*/
    /** method:GET
    *   url:/web/priority/item?...
    *   demo:/web/priority/item
    *   获取权限分页数据
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
    *              id         (Long)主键 
    *              priorityName         (String)权限名 
    *              canInsert         (Boolean)新增 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canDelete         (Boolean)删除 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canUpdate         (Boolean)编辑 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canQuery         (Boolean)查询 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canTruncate         (Boolean)清空 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canExport         (Boolean)导出 [{"":"全部"},{"false":"否"},{"true":"是"}]
    *              canImport         (Boolean)导入 [{"":"全部"},{"false":"否"},{"true":"是"}]
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

        List<Priority> pageList = this.priorityService.getPriorityList(query);
        Priority priority = null;
        if(pageList!=null && pageList.size()>0){
            priority = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("priority",priority);
            modelMap.addAttribute("priorityService",priorityService);
            modelMap.addAttribute("priorityProxyService",priorityProxyService);
            return "web/priority/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,priority);
            return null;
        }
    }



}
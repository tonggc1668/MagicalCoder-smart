package com.magicalcoder.youyamvc.web.controller.web.module;
import com.magicalcoder.youyamvc.app.module.service.ModuleService;
import com.magicalcoder.youyamvc.app.module.service.ModuleProxyService;
import com.magicalcoder.youyamvc.app.module.constant.ModuleConstant;
import com.magicalcoder.youyamvc.app.module.util.ModuleUtil;
import com.magicalcoder.youyamvc.app.model.Module;
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
@RequestMapping({"/web/module"})
@Controller
public class ModuleListController extends BaseController
{
    @Resource
    private ModuleService moduleService;
    @Resource
    private ModuleProxyService moduleProxyService;

    /**  method:GET
    *   url:/web/module/list/{pageIndex}/{pageSize}?...
    *   demo:/web/module/list/1/20
    *   获取模块分页数据
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
    *  否   @moduleName                     (String)模块唯一键 
    *  否   @moduleUrl                     (String)模块url 
    *  否   @moduleCategoryId                     (Long)模块分类 
    *  否   @sortNum                     (Integer)排序 
    *  否   @moduleTitle                     (String)模块标题 
    *  否  like @moduleNameFirst                     (String)模块唯一键 
    *  否  like @moduleUrlFirst                     (String)模块url 
    *  否   @moduleCategoryIdFirst                     (Long)模块分类 
    *  否  like @moduleTitleFirst                     (String)模块标题 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)主键 
    *              moduleName         (String)模块唯一键 
    *              moduleUrl         (String)模块url 
    *              moduleCategoryId         (Long)模块分类 
    *              sortNum         (Integer)排序 
    *              moduleTitle         (String)模块标题 
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
        @RequestParam(required = false,value ="moduleName")   String moduleName,
        @RequestParam(required = false,value ="moduleUrl")   String moduleUrl,
        @RequestParam(required = false,value ="moduleCategoryId")   Long moduleCategoryId,
        @RequestParam(required = false,value ="sortNum")   Integer sortNum,
        @RequestParam(required = false,value ="moduleTitle")   String moduleTitle,
        @RequestParam(required = false,value ="moduleNameFirst")                        String moduleNameFirst ,
        @RequestParam(required = false,value ="moduleUrlFirst")                        String moduleUrlFirst ,
        @RequestParam(required = false,value ="moduleCategoryIdFirst")                        Long moduleCategoryIdFirst ,
        @RequestParam(required = false,value ="moduleTitleFirst")                        String moduleTitleFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,module_name,module_url,module_category_id,sort_num,module_title,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(ModuleConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "moduleName",moduleName,
         "moduleUrl",moduleUrl,
         "moduleCategoryId",moduleCategoryId,
         "sortNum",sortNum,
         "moduleTitle",moduleTitle,
                "moduleNameFirst",moduleNameFirst ,
                "moduleUrlFirst",moduleUrlFirst ,
                "moduleCategoryIdFirst",moduleCategoryIdFirst ,
                "moduleTitleFirst",moduleTitleFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Module> pageList = this.moduleService.getModuleList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.moduleService.getModuleListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("moduleService",moduleService);
            modelMap.addAttribute("moduleProxyService",moduleProxyService);
            return "web/module/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(Module entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("moduleName",entity.getModuleName());
        map.put("moduleUrl",entity.getModuleUrl());
        map.put("moduleCategoryId",entity.getModuleCategoryId());
        map.put("sortNum",entity.getSortNum());
        map.put("moduleTitle",entity.getModuleTitle());
        return map;
    }*/
    /** method:GET
    *   url:/web/module/item?...
    *   demo:/web/module/item
    *   获取模块分页数据
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
    *              moduleName         (String)模块唯一键 
    *              moduleUrl         (String)模块url 
    *              moduleCategoryId         (Long)模块分类 
    *              sortNum         (Integer)排序 
    *              moduleTitle         (String)模块标题 
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

        List<Module> pageList = this.moduleService.getModuleList(query);
        Module module = null;
        if(pageList!=null && pageList.size()>0){
            module = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("module",module);
            modelMap.addAttribute("moduleService",moduleService);
            modelMap.addAttribute("moduleProxyService",moduleProxyService);
            return "web/module/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,module);
            return null;
        }
    }



}
package com.magicalcoder.youyamvc.web.controller.web.modulecategory;
import com.magicalcoder.youyamvc.app.modulecategory.service.ModuleCategoryService;
import com.magicalcoder.youyamvc.app.modulecategory.service.ModuleCategoryProxyService;
import com.magicalcoder.youyamvc.app.modulecategory.constant.ModuleCategoryConstant;
import com.magicalcoder.youyamvc.app.modulecategory.util.ModuleCategoryUtil;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;
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
@RequestMapping({"/web/module_category"})
@Controller
public class ModuleCategoryListController extends BaseController
{
    @Resource
    private ModuleCategoryService moduleCategoryService;
    @Resource
    private ModuleCategoryProxyService moduleCategoryProxyService;

    /**  method:GET
    *   url:/web/module_category/list/{pageIndex}/{pageSize}?...
    *   demo:/web/module_category/list/1/20
    *   获取模块分类分页数据
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
    *  否   @moduleCategoryName                     (String)模块名称 
    *  否   @sortNum                     (Integer)排序 
    *  否  like @moduleCategoryNameFirst                     (String)模块名称 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)主键 
    *              moduleCategoryName         (String)模块名称 
    *              sortNum         (Integer)排序 
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
        @RequestParam(required = false,value ="moduleCategoryName")   String moduleCategoryName,
        @RequestParam(required = false,value ="sortNum")   Integer sortNum,
        @RequestParam(required = false,value ="moduleCategoryNameFirst")                        String moduleCategoryNameFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,module_category_name,sort_num,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(ModuleCategoryConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "moduleCategoryName",moduleCategoryName,
         "sortNum",sortNum,
                "moduleCategoryNameFirst",moduleCategoryNameFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<ModuleCategory> pageList = this.moduleCategoryService.getModuleCategoryList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.moduleCategoryService.getModuleCategoryListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("moduleCategoryService",moduleCategoryService);
            modelMap.addAttribute("moduleCategoryProxyService",moduleCategoryProxyService);
            return "web/modulecategory/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(ModuleCategory entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("moduleCategoryName",entity.getModuleCategoryName());
        map.put("sortNum",entity.getSortNum());
        return map;
    }*/
    /** method:GET
    *   url:/web/module_category/item?...
    *   demo:/web/module_category/item
    *   获取模块分类分页数据
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
    *              moduleCategoryName         (String)模块名称 
    *              sortNum         (Integer)排序 
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

        List<ModuleCategory> pageList = this.moduleCategoryService.getModuleCategoryList(query);
        ModuleCategory moduleCategory = null;
        if(pageList!=null && pageList.size()>0){
            moduleCategory = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("moduleCategory",moduleCategory);
            modelMap.addAttribute("moduleCategoryService",moduleCategoryService);
            modelMap.addAttribute("moduleCategoryProxyService",moduleCategoryProxyService);
            return "web/modulecategory/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,moduleCategory);
            return null;
        }
    }



}
package com.magicalcoder.youyamvc.web.controller.web.dict;
import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.dict.service.DictProxyService;
import com.magicalcoder.youyamvc.app.dict.constant.DictConstant;
import com.magicalcoder.youyamvc.app.dict.util.DictUtil;
import com.magicalcoder.youyamvc.app.model.Dict;
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
@RequestMapping({"/web/dict"})
@Controller
public class DictListController extends BaseController
{
    @Resource
    private DictService dictService;
    @Resource
    private DictProxyService dictProxyService;

    /**  method:GET
    *   url:/web/dict/list/{pageIndex}/{pageSize}?...
    *   demo:/web/dict/list/1/20
    *   获取字典表分页数据
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
    *  否   @id                     (Long) 
    *  否   @dictKey                     (String) 
    *  否   @dictValue                     (String) 
    *  否   @dictType                     (Integer) 
    *  否   @dictDesc                     (String) 
    *  否   @dictOrder                     (Integer) 
    *  否   @dictKeyFirst                     (String) 
    *  否   @dictValueFirst                     (String) 
    *  否   @dictTypeFirst                     (Integer) 
    *  否   @dictDescFirst                     (String) 
    *  否   @dictOrderFirst                     (Integer) 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long) 
    *              dictKey         (String) 
    *              dictValue         (String) 
    *              dictType         (Integer) 
    *              dictDesc         (String) 
    *              dictOrder         (Integer) 
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
        @RequestParam(required = false,value ="dictKey")   String dictKey,
        @RequestParam(required = false,value ="dictValue")   String dictValue,
        @RequestParam(required = false,value ="dictType")   Integer dictType,
        @RequestParam(required = false,value ="dictDesc")   String dictDesc,
        @RequestParam(required = false,value ="dictOrder")   Integer dictOrder,
        @RequestParam(required = false,value ="dictKeyFirst")                        String dictKeyFirst ,
        @RequestParam(required = false,value ="dictValueFirst")                        String dictValueFirst ,
        @RequestParam(required = false,value ="dictTypeFirst")                        Integer dictTypeFirst ,
        @RequestParam(required = false,value ="dictDescFirst")                        String dictDescFirst ,
        @RequestParam(required = false,value ="dictOrderFirst")                        Integer dictOrderFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,dict_key,dict_value,dict_type,dict_desc,dict_order,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(DictConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "dictKey",dictKey,
         "dictValue",dictValue,
         "dictType",dictType,
         "dictDesc",dictDesc,
         "dictOrder",dictOrder,
                "dictKeyFirst",dictKeyFirst ,
                "dictValueFirst",dictValueFirst ,
                "dictTypeFirst",dictTypeFirst ,
                "dictDescFirst",dictDescFirst ,
                "dictOrderFirst",dictOrderFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Dict> pageList = this.dictService.getDictList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.dictService.getDictListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("dictService",dictService);
            modelMap.addAttribute("dictProxyService",dictProxyService);
            return "web/dict/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(Dict entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("dictKey",entity.getDictKey());
        map.put("dictValue",entity.getDictValue());
        map.put("dictType",entity.getDictType());
        map.put("dictDesc",entity.getDictDesc());
        map.put("dictOrder",entity.getDictOrder());
        return map;
    }*/
    /** method:GET
    *   url:/web/dict/item?...
    *   demo:/web/dict/item
    *   获取字典表分页数据
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
    *              id         (Long) 
    *              dictKey         (String) 
    *              dictValue         (String) 
    *              dictType         (Integer) 
    *              dictDesc         (String) 
    *              dictOrder         (Integer) 
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

        List<Dict> pageList = this.dictService.getDictList(query);
        Dict dict = null;
        if(pageList!=null && pageList.size()>0){
            dict = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("dict",dict);
            modelMap.addAttribute("dictService",dictService);
            modelMap.addAttribute("dictProxyService",dictProxyService);
            return "web/dict/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,dict);
            return null;
        }
    }



}
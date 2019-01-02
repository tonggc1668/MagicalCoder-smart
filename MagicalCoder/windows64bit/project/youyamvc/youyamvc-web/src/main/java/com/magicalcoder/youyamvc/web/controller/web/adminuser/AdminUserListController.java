package com.magicalcoder.youyamvc.web.controller.web.adminuser;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserProxyService;
import com.magicalcoder.youyamvc.app.adminuser.constant.AdminUserConstant;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserUtil;
import com.magicalcoder.youyamvc.app.model.AdminUser;
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
@RequestMapping({"/web/admin_user"})
@Controller
public class AdminUserListController extends BaseController
{
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private AdminUserProxyService adminUserProxyService;

    /**  method:GET
    *   url:/web/admin_user/list/{pageIndex}/{pageSize}?...
    *   demo:/web/admin_user/list/1/20
    *   获取管理员表分页数据
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
    *  否   @userName                     (String)用户名 
    *  否   @password                     (String)密码 
    *  否   @realName                     (String)真名 
    *  否   @email                     (String)邮箱 
    *  否   @telephone                     (String)座机号 
    *  否   @mobilePhone                     (String)手机号 
    *  否   @address                     (String)手机号 
    *  否   @createTime                     (Date)创建时间 
    *  否   @updateTime                     (Date)更新时间 
    *  否   @superAdmin                     (Integer)是否超级管理员 [{"":"全部"},{"0":"否"},{"1":"是"}]
    *  否   @roleId                     (Long)角色 
    *  否   @idFirst                     (Long)主键 
    *  否  like @userNameFirst                     (String)用户名 
    *  否  like @realNameFirst                     (String)真名 
    *  否   @emailFirst                     (String)邮箱 
    *  否  >= @createTimeFirst                     (Date)创建时间 
    *  否  <= @createTimeSecond                  (Date)创建时间 
    *  否  >= @updateTimeFirst                     (Date)更新时间 
    *  否  <= @updateTimeSecond                  (Date)更新时间 
    *  否   @superAdminFirst                     (Integer)是否超级管理员 [{"":"全部"},{"0":"否"},{"1":"是"}]
    *  否   @roleIdFirst                     (Long)角色 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)主键 
    *              userName         (String)用户名 
    *              password         (String)密码 
    *              realName         (String)真名 
    *              email         (String)邮箱 
    *              telephone         (String)座机号 
    *              mobilePhone         (String)手机号 
    *              address         (String)手机号 
    *              createTime         (Date)创建时间 
    *              updateTime         (Date)更新时间 
    *              superAdmin         (Integer)是否超级管理员 [{"":"全部"},{"0":"否"},{"1":"是"}]
    *              roleId         (Long)角色 
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
        @RequestParam(required = false,value ="userName")   String userName,
        @RequestParam(required = false,value ="password")   String password,
        @RequestParam(required = false,value ="realName")   String realName,
        @RequestParam(required = false,value ="email")   String email,
        @RequestParam(required = false,value ="telephone")   String telephone,
        @RequestParam(required = false,value ="mobilePhone")   String mobilePhone,
        @RequestParam(required = false,value ="address")   String address,
        @RequestParam(required = false,value ="createTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTime,
        @RequestParam(required = false,value ="updateTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTime,
        @RequestParam(required = false,value ="superAdmin")   Integer superAdmin,
        @RequestParam(required = false,value ="roleId")   Long roleId,
        @RequestParam(required = false,value ="idFirst")                        Long idFirst ,
        @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
        @RequestParam(required = false,value ="realNameFirst")                        String realNameFirst ,
        @RequestParam(required = false,value ="emailFirst")                        String emailFirst ,
        @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeFirst ,
        @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeSecond ,
        @RequestParam(required = false,value ="updateTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTimeFirst ,
        @RequestParam(required = false,value ="updateTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTimeSecond ,
        @RequestParam(required = false,value ="superAdminFirst")                        Integer superAdminFirst ,
        @RequestParam(required = false,value ="roleIdFirst")                        Long roleIdFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,user_name,password,real_name,email,telephone,mobile_phone,address,create_time,update_time,super_admin,role_id,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(AdminUserConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "userName",userName,
         "password",password,
         "realName",realName,
         "email",email,
         "telephone",telephone,
         "mobilePhone",mobilePhone,
         "address",address,
         "createTime",createTime,
         "updateTime",updateTime,
         "superAdmin",superAdmin,
         "roleId",roleId,
                "idFirst",idFirst ,
                "userNameFirst",userNameFirst ,
                "realNameFirst",realNameFirst ,
                "emailFirst",emailFirst ,
                "createTimeFirst",createTimeFirst ,
                "createTimeSecond",createTimeSecond ,
                "updateTimeFirst",updateTimeFirst ,
                "updateTimeSecond",updateTimeSecond ,
                "superAdminFirst",superAdminFirst ,
                "roleIdFirst",roleIdFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<AdminUser> pageList = this.adminUserService.getAdminUserList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.adminUserService.getAdminUserListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("adminUserService",adminUserService);
            modelMap.addAttribute("adminUserProxyService",adminUserProxyService);
            return "web/adminuser/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(AdminUser entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("userName",entity.getUserName());
        map.put("password",entity.getPassword());
        map.put("realName",entity.getRealName());
        map.put("email",entity.getEmail());
        map.put("telephone",entity.getTelephone());
        map.put("mobilePhone",entity.getMobilePhone());
        map.put("address",entity.getAddress());
        map.put("createTime",entity.getCreateTime());
        map.put("updateTime",entity.getUpdateTime());
        map.put("superAdmin",entity.getSuperAdmin());
        map.put("roleId",entity.getRoleId());
        return map;
    }*/
    /** method:GET
    *   url:/web/admin_user/item?...
    *   demo:/web/admin_user/item
    *   获取管理员表分页数据
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
    *              userName         (String)用户名 
    *              password         (String)密码 
    *              realName         (String)真名 
    *              email         (String)邮箱 
    *              telephone         (String)座机号 
    *              mobilePhone         (String)手机号 
    *              address         (String)手机号 
    *              createTime         (Date)创建时间 
    *              updateTime         (Date)更新时间 
    *              superAdmin         (Integer)是否超级管理员 [{"":"全部"},{"0":"否"},{"1":"是"}]
    *              roleId         (Long)角色 
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

        List<AdminUser> pageList = this.adminUserService.getAdminUserList(query);
        AdminUser adminUser = null;
        if(pageList!=null && pageList.size()>0){
            adminUser = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("adminUser",adminUser);
            modelMap.addAttribute("adminUserService",adminUserService);
            modelMap.addAttribute("adminUserProxyService",adminUserProxyService);
            return "web/adminuser/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,adminUser);
            return null;
        }
    }



}
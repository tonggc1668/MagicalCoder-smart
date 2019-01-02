package com.magicalcoder.youyamvc.web.controller.web.userweb;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebProxyService;
import com.magicalcoder.youyamvc.app.userweb.constant.UserWebConstant;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;
import com.magicalcoder.youyamvc.app.model.UserWeb;
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
@RequestMapping({"/web/user_web"})
@Controller
public class UserWebListController extends BaseController
{
    @Resource
    private UserWebService userWebService;
    @Resource
    private UserWebProxyService userWebProxyService;

    /**  method:GET
    *   url:/web/user_web/list/{pageIndex}/{pageSize}?...
    *   demo:/web/user_web/list/1/20
    *   获取用户表分页数据
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
    *  否   @id                     (Long)用户表 
    *  否   @userName                     (String)登录名称 
    *  否   @userPassword                     (String)登录密码存储加密后的值 
    *  否   @realName                     (String)用户真名 
    *  否   @scoreAmount                     (BigDecimal)积分余额 
    *  否   @moneyAmount                     (BigDecimal)现金余额 
    *  否   @registTime                     (Date)注册时间 
    *  否   @lastLoginTime                     (Date)最后登录时间 
    *  否   @accountStatus                     (Integer)账号状态0无效1有效 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *  否   @sex                     (Integer)性别1男0女 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *  否   @birthday                     (Date)生日 
    *  否   @headImgSrc                     (String)头像地址 
    *  否   @accountLevel                     (Integer)账号等级 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *  否   @mobile                     (String)手机号 
    *  否   @nickname                     (String)昵称 
    *  否   @twoCodeImgSrc                     (String)二维码图片 
    *  否   @userNameFirst                     (String)登录名称 
    *  否   @registTimeFirst                     (Date)注册时间 
    *  返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:
                pageCount://总数目
                pageList:[{
    *              id         (Long)用户表 
    *              userName         (String)登录名称 
    *              userPassword         (String)登录密码存储加密后的值 
    *              realName         (String)用户真名 
    *              scoreAmount         (BigDecimal)积分余额 
    *              moneyAmount         (BigDecimal)现金余额 
    *              registTime         (Date)注册时间 
    *              lastLoginTime         (Date)最后登录时间 
    *              accountStatus         (Integer)账号状态0无效1有效 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              sex         (Integer)性别1男0女 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              birthday         (Date)生日 
    *              headImgSrc         (String)头像地址 
    *              accountLevel         (Integer)账号等级 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              mobile         (String)手机号 
    *              nickname         (String)昵称 
    *              twoCodeImgSrc         (String)二维码图片 
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
        @RequestParam(required = false,value ="userPassword")   String userPassword,
        @RequestParam(required = false,value ="realName")   String realName,
        @RequestParam(required = false,value ="scoreAmount")   BigDecimal scoreAmount,
        @RequestParam(required = false,value ="moneyAmount")   BigDecimal moneyAmount,
        @RequestParam(required = false,value ="registTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date registTime,
        @RequestParam(required = false,value ="lastLoginTime")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date lastLoginTime,
        @RequestParam(required = false,value ="accountStatus")   Integer accountStatus,
        @RequestParam(required = false,value ="sex")   Integer sex,
        @RequestParam(required = false,value ="birthday")      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date birthday,
        @RequestParam(required = false,value ="headImgSrc")   String headImgSrc,
        @RequestParam(required = false,value ="accountLevel")   Integer accountLevel,
        @RequestParam(required = false,value ="mobile")   String mobile,
        @RequestParam(required = false,value ="nickname")   String nickname,
        @RequestParam(required = false,value ="twoCodeImgSrc")   String twoCodeImgSrc,
        @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
        @RequestParam(required = false,value ="registTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date registTimeFirst ,
        @RequestParam(required = false,value ="modelView") String modelView ,
        HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)
    {
        String orderBySqlFieldStr = ",id,user_name,user_password,real_name,score_amount,money_amount,regist_time,last_login_time,account_status,sex,birthday,head_img_src,account_level,mobile,nickname,two_code_img_src,";
        orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBy);
        pageSize = Math.min(UserWebConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "id",id,
         "userName",userName,
         "userPassword",userPassword,
         "realName",realName,
         "scoreAmount",scoreAmount,
         "moneyAmount",moneyAmount,
         "registTime",registTime,
         "lastLoginTime",lastLoginTime,
         "accountStatus",accountStatus,
         "sex",sex,
         "birthday",birthday,
         "headImgSrc",headImgSrc,
         "accountLevel",accountLevel,
         "mobile",mobile,
         "nickname",nickname,
         "twoCodeImgSrc",twoCodeImgSrc,
                "userNameFirst",userNameFirst ,
                "registTimeFirst",registTimeFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<UserWeb> pageList = this.userWebService.getUserWebList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.userWebService.getUserWebListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("ajaxData",ajaxData);
            modelMap.addAttribute("userWebService",userWebService);
            modelMap.addAttribute("userWebProxyService",userWebProxyService);
            return "web/userweb/list.vm";
        }else {
            toWebSuccessJson(response, callback,encode,ajaxData);
            return null;
        }
    }

   /* private Map<String,Object> toMap(UserWeb entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("userName",entity.getUserName());
        map.put("userPassword",entity.getUserPassword());
        map.put("realName",entity.getRealName());
        map.put("scoreAmount",entity.getScoreAmount());
        map.put("moneyAmount",entity.getMoneyAmount());
        map.put("registTime",entity.getRegistTime());
        map.put("lastLoginTime",entity.getLastLoginTime());
        map.put("accountStatus",entity.getAccountStatus());
        map.put("sex",entity.getSex());
        map.put("birthday",entity.getBirthday());
        map.put("headImgSrc",entity.getHeadImgSrc());
        map.put("accountLevel",entity.getAccountLevel());
        map.put("mobile",entity.getMobile());
        map.put("nickname",entity.getNickname());
        map.put("twoCodeImgSrc",entity.getTwoCodeImgSrc());
        return map;
    }*/
    /** method:GET
    *   url:/web/user_web/item?...
    *   demo:/web/user_web/item
    *   获取用户表分页数据
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
    *              id         (Long)用户表 
    *              userName         (String)登录名称 
    *              userPassword         (String)登录密码存储加密后的值 
    *              realName         (String)用户真名 
    *              scoreAmount         (BigDecimal)积分余额 
    *              moneyAmount         (BigDecimal)现金余额 
    *              registTime         (Date)注册时间 
    *              lastLoginTime         (Date)最后登录时间 
    *              accountStatus         (Integer)账号状态0无效1有效 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              sex         (Integer)性别1男0女 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              birthday         (Date)生日 
    *              headImgSrc         (String)头像地址 
    *              accountLevel         (Integer)账号等级 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              mobile         (String)手机号 
    *              nickname         (String)昵称 
    *              twoCodeImgSrc         (String)二维码图片 
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

        List<UserWeb> pageList = this.userWebService.getUserWebList(query);
        UserWeb userWeb = null;
        if(pageList!=null && pageList.size()>0){
            userWeb = pageList.get(0);
        }
        if("vm".equals(modelView)){
            modelMap.addAttribute("userWeb",userWeb);
            modelMap.addAttribute("userWebService",userWebService);
            modelMap.addAttribute("userWebProxyService",userWebProxyService);
            return "web/userweb/item.vm";
        }else {
            toWebSuccessJson(response, callback,encode,userWeb);
            return null;
        }
    }



}
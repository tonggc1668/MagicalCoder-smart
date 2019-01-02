package com.magicalcoder.youyamvc.web.controller.web.user.userweb;

import com.magicalcoder.youyamvc.app.model.UserWeb;
import com.magicalcoder.youyamvc.app.userweb.constant.UserWebConstant;
import com.magicalcoder.youyamvc.app.userweb.dto.UserWebRegistDto;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;
import com.magicalcoder.youyamvc.app.userweb.util.ValidCodeUtil;
import com.magicalcoder.youyamvc.core.common.dto.JsonData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.web.WebLoginController;
import com.magicalcoder.youyamvc.web.common.StatusConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzwww.magicalcoder.com on 2016/1/19.
 */
@Controller
@RequestMapping("/user/user_web_login/")
public class UserWebLoginController extends WebLoginController{

    @Resource
    private UserWebService userWebService;

    //更新验证原手机号码
    @RequestMapping(value = "valid_change_mobile/{validCode}",method = RequestMethod.GET)
    public void changeMobileOrigin(@PathVariable String validCode
            ,HttpServletRequest request,HttpServletResponse response){
        if(StringUtils.isBlank(validCode)){
            toWebFailureJson(response,"入参为空");
            return;
        }
        String sessionId = request.getSession().getId();
        String storedValidCode = ValidCodeUtil.getValidCodeFromCache(UserWebConstant.VALID_CODE_USE_CHANGE_MOBILE, sessionId);
        if(!validCode.equals(storedValidCode)){
            toJsonData(response, new JsonData.Builder(null).code(StatusConstant.VALID_PARAM).message("验证码不正确").build());
        }else {
            toWebSuccessJson(response);
        }
    }
    //更新新手机号码
    @RequestMapping(value = "update_mobile")
    public void updateMobile(@RequestParam(required = true,value = "mobile") String mobile,
                             @RequestParam(required = true,value = "validCode") String validCode,
                             @RequestParam(required = true,value = "oldValidCode") String oldValidCode
            ,HttpServletRequest request,HttpServletResponse response){
        if(StringUtils.isBlank(mobile)||StringUtils.isBlank(validCode)||StringUtils.isBlank(oldValidCode)){
            toWebFailureJson(response,"入参为空");
            return;
        }
        String sessionId = request.getSession().getId();
        String storedValidCode = ValidCodeUtil.getValidCodeFromCache(UserWebConstant.VALID_CODE_USE_CHANGE_MOBILE, sessionId);
        if(!oldValidCode.equals(storedValidCode)){
            toWebFailureJson(response, "请先验证原手机");
        }else {
            //新验证码
            storedValidCode = ValidCodeUtil.getValidCodeFromCache(UserWebConstant.VALID_CODE_USE_UPDATE_MOBILE, sessionId);
            if(!validCode.equals(storedValidCode)){
                toWebFailureJson(response,"验证码不正确");
            }else {
                Long userId = UserWebUtil.userId(request);
                UserWeb entity = userWebService.getUserWeb(userId);
                entity.setMobile(mobile);
                userWebService.updateUserWeb(entity);
                toWebSuccessJson(response);
            }
        }
    }

    //更新用户信息
    @RequestMapping(value = "update_info")
    public void updateInfo(@ModelAttribute UserWeb userWeb,HttpServletRequest request,HttpServletResponse response){
        userWeb.setId(null);//避免拷贝id
        Long userId = UserWebUtil.userId(request);
        UserWeb entity = userWebService.getUserWeb(userId);
        if(entity!=null){
            //不能拷贝的对象
            UserWebRegistDto registDto = new UserWebRegistDto();//临时拷贝对象 限制了客户端可以更改的字段
            Copyer.copy(userWeb, registDto);
            Copyer.reflectCopyZeroNotNull(registDto,entity);
            userWebService.updateUserWeb(entity);
        }
        toWebSuccessJson(response);
    }


    //获取用户信息
    @RequestMapping(value = "info")
    public void info(HttpServletRequest request,HttpServletResponse response){
        toWebSuccessJson(response,UserWebUtil.getUserDtoFromCache(request));
    }



}

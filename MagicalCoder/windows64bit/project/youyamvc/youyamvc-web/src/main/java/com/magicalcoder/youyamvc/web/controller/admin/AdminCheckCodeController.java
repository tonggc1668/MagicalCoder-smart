package com.magicalcoder.youyamvc.web.controller.admin;

import com.magicalcoder.youyamvc.core.identifyingcode.CreateIdentifyingCode;
import com.magicalcoder.youyamvc.core.identifyingcode.IdentifyingCodeConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 后台人员登录
 *hdy qq:799374340
 *2013-6-8 下午5:05:27
 */
@Controller
public class AdminCheckCodeController {

    @RequestMapping(value="/admin/checkCode")
    public void checkCode(HttpServletRequest request,HttpServletResponse response,ModelMap model){
        CreateIdentifyingCode create = new CreateIdentifyingCode();
        try {
            create.create(IdentifyingCodeConstant.STORE_TYPE_CACHE_SERVER,IdentifyingCodeConstant.NUMBER_ENGLISH,request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

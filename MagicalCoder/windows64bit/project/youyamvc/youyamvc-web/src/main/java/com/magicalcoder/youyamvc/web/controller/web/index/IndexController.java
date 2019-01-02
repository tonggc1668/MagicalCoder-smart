package com.magicalcoder.youyamvc.web.controller.web.index;

import com.magicalcoder.youyamvc.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/5/8.
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String toIndex(ModelMap model){
        return "web/index.vm";
    }

}

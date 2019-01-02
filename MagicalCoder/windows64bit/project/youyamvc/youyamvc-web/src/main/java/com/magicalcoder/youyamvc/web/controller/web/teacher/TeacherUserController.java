package com.magicalcoder.youyamvc.web.controller.web.teacher;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherService;
import com.magicalcoder.youyamvc.app.teacher.constant.TeacherConstant;
import com.magicalcoder.youyamvc.app.teacher.util.TeacherUtil;
import com.magicalcoder.youyamvc.app.model.Teacher;
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
@RequestMapping({"/user/teacher"})
@Controller
public class TeacherUserController extends WebLoginController
{

    @Resource
    private TeacherService teacherService;


}
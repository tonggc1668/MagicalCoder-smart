<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- sidebar: style can be found in sidebar.less -->
<section class="sidebar">
    <!-- Sidebar user panel -->
    <div class="user-panel">
        <div class="pull-left image">
            <img src="assets/admin/img/avatar3.png" class="img-circle" alt="User Image" />
        </div>
        <div class="pull-left info">
            <p id="magicalCoderUserName"></p>
            <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
        </div>
    </div>
    <!-- 配置好a的id（按层定 ）即可 利用了cookie 自动记录当前点击的id  -->
    <ul class="sidebar-menu"  id="leftNavigate">

        <c:forEach items="${userLeftModules}" var="ModuleAndCategory">
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-dashboard"></i> <span>${ModuleAndCategory.category.moduleCategoryName}</span> <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <c:forEach var="module" items="${ModuleAndCategory.moduleList}">
                        <li><a id="${module.moduleName}" href="${module.moduleUrl}"><i class="fa fa-circle-o"></i> ${module.moduleTitle}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
        <%--<li class="treeview">
            <a href="#">
                <i class="fa fa-dashboard"></i> <span>前台用户管理</span> <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="treeview-menu">
                <li><a id="1-1" href="admin/user_web/list"><i class="fa fa-circle-o"></i> 用户列表</a></li>
               &lt;%&ndash; <li><a id="2-32" href=""><i class="fa fa-circle-o"></i> 其他列表</a></li>&ndash;%&gt;

            </ul>
        </li>

        <li class="treeview">
            <a href="#">
                <i class="fa fa-laptop"></i>
                <span>后台用户管理</span>
                <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="treeview-menu">
                <li><a id="2-1" href="admin/admin_user/list"><i class="fa fa-circle-o"></i> 用户列表</a></li>
            </ul>
        </li>
        <li class="treeview">
            <a href="#">
                <i class="fa fa-laptop"></i>
                <span>演示功能</span>
                <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="treeview-menu">
                <li><a id="3-10" href="admin/student/list"><i class="fa fa-circle-o"></i> 学生列表</a></li>
                <li><a id="3-1" href="admin/school/list"><i class="fa fa-circle-o"></i> 学校列表</a></li>
                <li><a id="3-2" href="admin/classes/list"><i class="fa fa-circle-o"></i> 班级列表</a></li>
                <li><a id="3-3" href="admin/teacher/list"><i class="fa fa-circle-o"></i> 老师列表</a></li>
                <li><a id="3-4" href="admin/class_teacher/list"><i class="fa fa-circle-o"></i> 班级关联老师列表</a></li>
            </ul>
        </li>
        <li class="treeview">
            <a href="#">
                <i class="fa fa-laptop"></i>
                <span>角色权限</span>
                <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="treeview-menu">
                <li><a id="4-0" href="admin/role/list"><i class="fa fa-circle-o"></i> 角色</a></li>
                <li><a id="4-11" href="admin/module_category/list"><i class="fa fa-circle-o"></i> 模块分类</a></li>
                <li><a id="4-1" href="admin/module/list"><i class="fa fa-circle-o"></i> 模块</a></li>
                <li><a id="4-2" href="admin/priority/list"><i class="fa fa-circle-o"></i> 权限</a></li>
                <li><a id="4-3" href="admin/role_module_priority/list"><i class="fa fa-circle-o"></i> 角色模块权限关联</a></li>
            </ul>
        </li>--%>
    </ul>
</section>
<script src="assets/admin/app/left.js" type="text/javascript"></script>

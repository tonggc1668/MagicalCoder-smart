

<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%--
 Created by www.magicalcoder.com
 如果你改变了此类 read 请将此行删除
 799374340@qq.com
--%><!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/head.jsp"%>
    <title>AdminLTE | Dashboard</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <%@include file="../include/head.jsp"%>
</head>
<body class="skin-blue">
    <header class="header">
        <%@include file="../include/top.jsp"%>
    </header>
    <div class="wrapper row-offcanvas row-offcanvas-left">
        <aside class="left-side sidebar-offcanvas">
            <%@include file="../include/left.jsp"%>
        </aside>
        <aside class="right-side">
            <section class="content-header">
                <h1>
                    管理员表详情
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="#">Tables</a></li>
                    <li class="active">Data tables</li>
                </ol>
            </section>
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary">
                            <form class="form-horizontal" role="form" id="form" action="admin/admin_user/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${adminUser.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                    <h3 class="box-title alert-danger">${exceptionMsg}</h3>
                                </div>
                                    <input type="hidden" name="id" value="${adminUser.id}">

                                <div class="box-body">
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            用户名
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="userName"
                                                   name="userName" title="用户名"  placeholder="请输入用户名"
                                                             value="${adminUser.userName}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            密码
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="password"
                                                   name="password" title="密码"  placeholder="请输入null"
                                                             value="${adminUser.password}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            真名
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="realName"
                                                   name="realName" title="真名"  placeholder="请输入真名"
                                                             value="${adminUser.realName}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            邮箱
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="email"
                                                   name="email" title="邮箱"  placeholder="请输入邮箱"
                                                             value="${adminUser.email}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            座机号
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="telephone"
                                                   name="telephone" title="座机号"  placeholder="请输入座机号"
                                                             value="${adminUser.telephone}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            手机号
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="mobilePhone"
                                                   name="mobilePhone" title="手机号"  placeholder="请输入手机号"
                                                             value="${adminUser.mobilePhone}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            地址
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="address"
                                                   name="address" title="地址"  placeholder="请输入手机号"
                                                             value="${adminUser.address}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            创建时间
                                        </label>
                                        <div class="col-sm-9">
                                            <input id="createTime" type="text" name="createTime" class="Wdate form-control"                                                onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                               value="<fmt:formatDate value="${ adminUser.createTime }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            更新时间
                                        </label>
                                        <div class="col-sm-9">
                                            <input id="updateTime" type="text" name="updateTime" class="Wdate form-control"                                                onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                               value="<fmt:formatDate value="${ adminUser.updateTime }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            角色
                                        </label>
                                         <div class="col-sm-3">
                                            <input type="hidden" id="roleId"
                                                   name="roleId"
                                                   value="${adminUser.roleId}">
                                            <input type="text" class="form-control" id="roleIdSearch"
                                                  name="roleIdSearch"  placeholder="请输入关键词查询角色"
                                            <c:if test="${ role!=null }">
                                                         value="${role.roleName}"
                                            </c:if>
                                                   >
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                                <c:if test="${adminUser.roleId!=null}">
                                                    <a target="_blank" onclick="openUrl(this,'admin/role/detail_param?id=${adminUser.roleId}')"> 查看详情</a>
                                                </c:if>
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            是否超级管理员
                                        </label>
                                         <div class="col-sm-9">
                                             <select class="form-control" id="superAdmin" name="superAdmin" title="是否超级管理员"  >
                                                         <option
                                                 <c:if test="${ adminUser.superAdmin == 0 }">selected</c:if>
                                                 value="0" >否</option>
                                                         <option
                                                 <c:if test="${ adminUser.superAdmin == 1 }">selected</c:if>
                                                 value="1" >是</option>
                                             </select>
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <button type="button" onclick="submitForm('#form')" class="btn btn-primary">保存</button>
                                    <button type="button" onclick="history.go(-1);"  class="btn btn-primary">返回</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </aside>
    </div>
    <%@include file="../include/tail.jsp"%>

    <script charset="utf-8" src="assets/admin/app/base.js"></script>
    <script language="javascript" type="text/javascript" src="${CTX}assets/admin/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" >
        $(function(){
            //base.js
            validateForm("#form")
                    foreignSearch('role','roleId','','id');

        })
    </script>
</body>
</html>

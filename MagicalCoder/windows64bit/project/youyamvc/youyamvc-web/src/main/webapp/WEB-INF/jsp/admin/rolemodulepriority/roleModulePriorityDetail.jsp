

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
                    角色模块权限详情
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
                            <form class="form-horizontal" role="form" id="form" action="admin/role_module_priority/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${roleModulePriority.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                    <h3 class="box-title alert-danger">${exceptionMsg}</h3>
                                </div>
                                    <input type="hidden" name="id" value="${roleModulePriority.id}">

                                <div class="box-body">
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            角色
                                        </label>
                                         <div class="col-sm-3">
                                            <input type="hidden" id="roleId"
                                                   name="roleId"
                                                   value="${roleModulePriority.roleId}">
                                            <input type="text" class="form-control" id="roleIdSearch"
                                                  name="roleIdSearch"  placeholder="请输入关键词查询角色"
                                            <c:if test="${ role!=null }">
                                                         value="${role.roleName}"
                                            </c:if>
                                                   >
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                                <c:if test="${roleModulePriority.roleId!=null}">
                                                    <a target="_blank" onclick="openUrl(this,'admin/role/detail_param?id=${roleModulePriority.roleId}')"> 查看详情</a>
                                                </c:if>
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            模块
                                        </label>
                                         <div class="col-sm-3">
                                            <input type="hidden" id="moduleId"
                                                   name="moduleId"
                                                   value="${roleModulePriority.moduleId}">
                                            <input type="text" class="form-control" id="moduleIdSearch"
                                                  name="moduleIdSearch"  placeholder="请输入关键词查询模块"
                                            <c:if test="${ module!=null }">
                                                         value="${module.moduleName}-${module.moduleTitle}"
                                            </c:if>
                                                   >
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                                <c:if test="${roleModulePriority.moduleId!=null}">
                                                    <a target="_blank" onclick="openUrl(this,'admin/module/detail_param?id=${roleModulePriority.moduleId}')"> 查看详情</a>
                                                </c:if>
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            权限
                                        </label>
                                         <div class="col-sm-3">
                                            <input type="hidden" id="priorityId"
                                                   name="priorityId"
                                                   value="${roleModulePriority.priorityId}">
                                            <input type="text" class="form-control" id="priorityIdSearch"
                                                  name="priorityIdSearch"  placeholder="请输入关键词查询权限"
                                            <c:if test="${ priority!=null }">
                                                         value="${priority.priorityName}"
                                            </c:if>
                                                   >
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                                <c:if test="${roleModulePriority.priorityId!=null}">
                                                    <a target="_blank" onclick="openUrl(this,'admin/priority/detail_param?id=${roleModulePriority.priorityId}')"> 查看详情</a>
                                                </c:if>
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
    <script type="text/javascript" >
        $(function(){
            //base.js
            validateForm("#form")
                    foreignSearch('role','roleId','','id');

                    foreignSearch('module','moduleId','','id');

                    foreignSearch('priority','priorityId','','id');

        })
    </script>
</body>
</html>

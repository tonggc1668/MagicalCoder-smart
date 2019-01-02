

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
                    权限详情
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
                            <form class="form-horizontal" role="form" id="form" action="admin/priority/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${priority.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                    <h3 class="box-title alert-danger">${exceptionMsg}</h3>
                                </div>
                                    <input type="hidden" name="id" value="${priority.id}">

                                <div class="box-body">
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            权限名
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="priorityName"
                                                   name="priorityName" title="权限名"  placeholder="请输入权限名"
                                                             value="${priority.priorityName}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            新增
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canInsert" title="新增"                                                  <c:if test="${!priority.canInsert }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canInsert" title="新增"                                                  <c:if test="${priority.canInsert }">checked</c:if>
                                               value="true" >是
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            删除
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canDelete" title="删除"                                                  <c:if test="${!priority.canDelete }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canDelete" title="删除"                                                  <c:if test="${priority.canDelete }">checked</c:if>
                                               value="true" >是
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            编辑
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canUpdate" title="编辑"                                                  <c:if test="${!priority.canUpdate }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canUpdate" title="编辑"                                                  <c:if test="${priority.canUpdate }">checked</c:if>
                                               value="true" >是
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            查询
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canQuery" title="查询"                                                  <c:if test="${!priority.canQuery }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canQuery" title="查询"                                                  <c:if test="${priority.canQuery }">checked</c:if>
                                               value="true" >是
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            清空
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canTruncate" title="清空"                                                  <c:if test="${!priority.canTruncate }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canTruncate" title="清空"                                                  <c:if test="${priority.canTruncate }">checked</c:if>
                                               value="true" >是
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            导出
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canExport" title="导出"                                                  <c:if test="${!priority.canExport }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canExport" title="导出"                                                  <c:if test="${priority.canExport }">checked</c:if>
                                               value="true" >是
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            导入
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="canImport" title="导入"                                                  <c:if test="${!priority.canImport }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="canImport" title="导入"                                                  <c:if test="${priority.canImport }">checked</c:if>
                                               value="true" >是
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
    <script type="text/javascript" >
        $(function(){
            //base.js
            validateForm("#form")
        })
    </script>
</body>
</html>

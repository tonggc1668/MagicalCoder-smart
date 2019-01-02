

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
                    班级关联教师详情
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
                            <form class="form-horizontal" role="form" id="form" action="admin/class_teacher/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${classTeacher.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                    <h3 class="box-title alert-danger">${exceptionMsg}</h3>
                                </div>
                                    <input type="hidden" name="id" value="${classTeacher.id}">

                                <div class="box-body">
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            班级表主键
                                        </label>
                                         <div class="col-sm-3">
                                            <input type="hidden" id="classId"
                                                   name="classId"
                                                   value="${classTeacher.classId}">
                                            <input type="text" class="form-control" id="classIdSearch"
                                                  name="classIdSearch"  placeholder="请输入关键词查询班级"
                                            <c:if test="${ classes!=null }">
                                                         value="${classes.className}"
                                            </c:if>
                                                   >
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                                <c:if test="${classTeacher.classId!=null}">
                                                    <a target="_blank" onclick="openUrl(this,'admin/classes/detail_param?id=${classTeacher.classId}')"> 查看详情</a>
                                                </c:if>
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            老师表主键
                                        </label>
                                         <div class="col-sm-3">
                                            <input type="hidden" id="teacherId"
                                                   name="teacherId"
                                                   value="${classTeacher.teacherId}">
                                            <input type="text" class="form-control" id="teacherIdSearch"
                                                  name="teacherIdSearch"  placeholder="请输入关键词查询教师"
                                            <c:if test="${ teacher!=null }">
                                                         value="${teacher.teacherName}"
                                            </c:if>
                                                   >
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                                <c:if test="${classTeacher.teacherId!=null}">
                                                    <a target="_blank" onclick="openUrl(this,'admin/teacher/detail_param?id=${classTeacher.teacherId}')"> 查看详情</a>
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
                    foreignSearch('classes','classId','','id');

                    foreignSearch('teacher','teacherId','','id');

        })
    </script>
</body>
</html>

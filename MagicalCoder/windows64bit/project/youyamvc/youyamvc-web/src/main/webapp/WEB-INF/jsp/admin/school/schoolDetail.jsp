

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
        <link rel="stylesheet" href="assets/admin/js/kindeditor-4.1.10/themes/default/default.css" />
        <link rel="stylesheet" href="assets/admin/js/kindeditor-4.1.10/plugins/code/prettify.css" />
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
                    学校详情
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
                            <form class="form-horizontal" role="form" id="form" action="admin/school/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${school.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                    <h3 class="box-title alert-danger">${exceptionMsg}</h3>
                                </div>
                                    <input type="hidden" name="id" value="${school.id}">

                                <div class="box-body">
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            学校名称
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="schoolName"
                                                   name="schoolName" title="学校名称"  placeholder="请输入学校名称"
                                                   required  chineseCharacter  minLength="0"            value="${school.schoolName}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            学校头像
                                        </label>
                                        <div class="col-sm-9">
                                            <img id="headImgReview"  src="${school.headImg}" />
                                            <input type="text" class="form-control "
                                                         id="headImg" name="headImg"
                                                         value="${school.headImg}" placeholder="">
                                            <input type="file" id="headImgFile"
                                                        name="myfiles" onchange="uploadFile('headImg','school')" class="file" />
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            班级个数
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="classCount"
                                                   name="classCount" title="班级个数"  placeholder="请输入班级个数"
                                                   digits  minLength="0"            value="${school.classCount}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            学校地址
                                        </label>
                                        <div class="col-sm-9">
                                            <textarea cols="100" rows="8"
                                                  class="form-control " id="adress"
                                                 name="adress" title="学校地址"  placeholder="请输入学校地址"
                                                         >${school.adress}</textarea>
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            学校类型
                                        </label>
                                         <div class="col-sm-9">
                                             <select class="form-control" id="schoolType" name="schoolType" title="学校类型"  >
                                                         <option
                                                 <c:if test="${ school.schoolType == 0 }">selected</c:if>
                                                 value="0" >普通</option>
                                                         <option
                                                 <c:if test="${ school.schoolType == 1 }">selected</c:if>
                                                 value="1" >重点</option>
                                             </select>
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            是否开学
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="radio" class="form-control " name="open" title="是否开学"                                                  <c:if test="${!school.open }">checked</c:if>
                                               value="false" >否
                                            <input type="radio" class="form-control " name="open" title="是否开学"                                                  <c:if test="${school.open }">checked</c:if>
                                               value="true" >是
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
                                               value="<fmt:formatDate value="${ school.createTime }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            学校描述
                                        </label>
                                        <div class="col-sm-9">
                                            <textarea cols="100" rows="20"                                                   class="form-control " id="schoolDesc"
                                                  style="width:1038px;height:600px;visibility:hidden;"
                                                  name="schoolDesc" title="学校描述"  placeholder="请输入学校描述"
                                                >${school.schoolDesc}</textarea>
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
                                               value="<fmt:formatDate value="${ school.updateTime }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
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
    <script type="text/javascript" src="assets/admin/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="assets/admin/js/kindeditor-4.1.10/kindeditor.js"></script>
    <script charset="utf-8" src="assets/admin/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
    <script charset="utf-8" src="assets/admin/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
    <script>
        $(function(){
            createKindEditor("schoolDesc");
        })
    </script>
    <script type="text/javascript" >
        $(function(){
            //base.js
            validateForm("#form")
        })
    </script>
</body>
</html>

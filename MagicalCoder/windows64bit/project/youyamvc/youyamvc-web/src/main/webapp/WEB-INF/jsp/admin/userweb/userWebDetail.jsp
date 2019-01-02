

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
                    用户表详情
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
                            <form class="form-horizontal" role="form" id="form" action="admin/user_web/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${userWeb.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                    <h3 class="box-title alert-danger">${exceptionMsg}</h3>
                                </div>
                                    <input type="hidden" name="id" value="${userWeb.id}">

                                <div class="box-body">
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            登录名称
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="userName"
                                                   name="userName" title="登录名称"  placeholder="请输入登录名称"
                                                             value="${userWeb.userName}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            登录密码存储加密后的值
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="userPassword"
                                                   name="userPassword" title="登录密码存储加密后的值"  placeholder="请输入登录密码存储加密后的值"
                                                             value="${userWeb.userPassword}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            用户真名
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="realName"
                                                   name="realName" title="用户真名"  placeholder="请输入用户真名"
                                                             value="${userWeb.realName}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            积分余额
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="scoreAmount"
                                                   name="scoreAmount" title="积分余额"  placeholder="请输入积分余额"
                                                             value="${userWeb.scoreAmount}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            现金余额
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="moneyAmount"
                                                   name="moneyAmount" title="现金余额"  placeholder="请输入现金余额"
                                                             value="${userWeb.moneyAmount}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            注册时间
                                        </label>
                                        <div class="col-sm-9">
                                            <input id="registTime" type="text" name="registTime" class="Wdate form-control"                                                onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                               value="<fmt:formatDate value="${ userWeb.registTime }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            最后登录时间
                                        </label>
                                        <div class="col-sm-9">
                                            <input id="lastLoginTime" type="text" name="lastLoginTime" class="Wdate form-control"                                                onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                               value="<fmt:formatDate value="${ userWeb.lastLoginTime }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            账号状态0无效1有效
                                        </label>
                                         <div class="col-sm-9">
                                             <select class="form-control" id="accountStatus" name="accountStatus" title="账号状态0无效1有效"  >
                                                         <option
                                                 <c:if test="${ userWeb.accountStatus == 0 }">selected</c:if>
                                                 value="0" >类型一</option>
                                                         <option
                                                 <c:if test="${ userWeb.accountStatus == 1 }">selected</c:if>
                                                 value="1" >类型二</option>
                                             </select>
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            性别1男0女
                                        </label>
                                         <div class="col-sm-9">
                                             <select class="form-control" id="sex" name="sex" title="性别1男0女"  >
                                                         <option
                                                 <c:if test="${ userWeb.sex == 0 }">selected</c:if>
                                                 value="0" >类型一</option>
                                                         <option
                                                 <c:if test="${ userWeb.sex == 1 }">selected</c:if>
                                                 value="1" >类型二</option>
                                             </select>
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            生日
                                        </label>
                                        <div class="col-sm-9">
                                            <input id="birthday" type="text" name="birthday" class="Wdate form-control"                                                onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                               value="<fmt:formatDate value="${ userWeb.birthday }"
                                               pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            头像地址
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="headImgSrc"
                                                   name="headImgSrc" title="头像地址"  placeholder="请输入头像地址"
                                                             value="${userWeb.headImgSrc}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            账号等级
                                        </label>
                                         <div class="col-sm-9">
                                             <select class="form-control" id="accountLevel" name="accountLevel" title="账号等级"  >
                                                         <option
                                                 <c:if test="${ userWeb.accountLevel == 0 }">selected</c:if>
                                                 value="0" >类型一</option>
                                                         <option
                                                 <c:if test="${ userWeb.accountLevel == 1 }">selected</c:if>
                                                 value="1" >类型二</option>
                                             </select>
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
                                            <input type="text" class="form-control " id="mobile"
                                                   name="mobile" title="手机号"  placeholder="请输入手机号"
                                                             value="${userWeb.mobile}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            昵称
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="nickname"
                                                   name="nickname" title="昵称"  placeholder="请输入昵称"
                                                             value="${userWeb.nickname}">
                                        </div>
                                        <label class="col-sm-2 control-label" >
                                            <span class="validateMsg"></span>
                                        </label>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-sm-1 control-label">
                                            二维码图片
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " id="twoCodeImgSrc"
                                                   name="twoCodeImgSrc" title="二维码图片"  placeholder="请输入二维码图片"
                                                             value="${userWeb.twoCodeImgSrc}">
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
        })
    </script>
</body>
</html>

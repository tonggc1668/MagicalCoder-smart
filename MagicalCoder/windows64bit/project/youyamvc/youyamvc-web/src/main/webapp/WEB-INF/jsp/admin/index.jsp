<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

    <%@include file="../common/head.jsp"%>
    <title>AdminLTE | Dashboard</title>
    <%--<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- bootstrap 3.0.2 -->
    <link href="assets/admin/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- font Awesome -->
    <link href="assets/admin/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="assets/admin/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Morris chart -->
    <link href="assets/admin/css/morris/morris.css" rel="stylesheet" type="text/css" />
    <!-- jvectormap -->
    <link href="assets/admin/css/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap wysihtml5 - text editor -->
    <link href="assets/admin/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="assets/admin/css/AdminLTE.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="assets/admin/js/jquery/1.8.2/jquery-min-1.8.2.js"></script>--%>
    <%@include file="include/head.jsp"%>

</head>
<body class="skin-blue">
<!-- header logo: style can be found in header.less -->
<header class="header">
    <%@include file="include/top.jsp"%>
</header>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="left-side sidebar-offcanvas">
        <%@include file="include/left.jsp"%>
    </aside>

    <!-- Right side column. Contains the navbar and content of the page -->
    <aside class="right-side">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Dashboard
                <small>Control panel</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Dashboard</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div>访问 <a target="_blank" href="http://www.magicalcoder.com/admin">MagicalCoder</a>注册用户名，然后登陆进去
                使用MagicalCoder提供的在线编辑工具，编辑完后点生成代码 然后把下载的模板解压覆盖到youyamvc
                重启项目 所有增删改查多表关联等功能便自动完成
            </div>

        </section><!-- /.content -->
    </aside><!-- /.right-side -->
</div><!-- ./wrapper -->

<%@include file="include/tail.jsp"%>

</body>
</html>
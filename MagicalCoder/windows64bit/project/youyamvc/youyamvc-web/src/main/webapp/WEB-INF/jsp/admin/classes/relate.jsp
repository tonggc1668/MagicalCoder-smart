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
                    $manyTableComment 关联列表
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li><a href="#">li</a></li>
                </ol>
            </section>
            <!-- one模型 -->
            <input type="hidden" name="${oneJavaField}" id="${oneJavaField}" value="${${oneJavaField}}">
            <!-- many 模型-->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <div class="box-header">
                                <div class="box-tools">
                                    <div class="form">
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-xs-2">
                                                    <div class="form-group">
                                                        <label for="querySubmit">&nbsp;</label>
                                                        <button class="btn btn-primary form-control" id="querySubmit">查询</button>
                                                    </div>
                                                </div>
                                                <input type="hidden" id="pageCount" value="0">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body table-responsive" style="overflow: auto">
                                <table id="example2" class="table table-bordered table-hover dataTable">
                                    <thead id="thead">
                                    <tr>
                                        <th><input type="checkbox" id="checkAll"></th>
                                        <th>序号</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="tbody">
                                    </tbody>
                                    <tfoot>
                                    </tfoot>
                                </table>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <div class="dataTables_info"></div>
                                    </div>
                                    <div class="col-xs-6">
                                        <div class="dataTables_paginate paging_bootstrap">
                                            <ul class="pagination" id="pagination">
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                </div>
            </section>
            <!-- /.content -->
        </aside>
        <!-- /.right-side -->
    </div>
    <!-- ./wrapper -->
    <%@include file="../include/tail.jsp"%>
    <script language="javascript" type="text/javascript"
            src="${CTX}assets/admin/js/My97DatePicker/WdatePicker.js"></script>
    <script src="assets/admin/app/list_page.js" type="text/javascript"></script>
    <script src="assets/admin/app/classes/detailRelateManyPaging.js" type="text/javascript"></script>
</body>
</html>




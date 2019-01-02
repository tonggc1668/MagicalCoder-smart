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
                学生列表 
                <a class="btn btn-app" href="admin/student/detail"><i class="fa fa-edit"></i>新增</a>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="#">li</a></li>
            </ol>
        </section>
        <!-- Main content -->
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
                                                     <label for="nameFirst">学生名称 (=)</label>
                                                     <input type="text" class="form-control"
                                                      value="${ nameFirst }"
                                                                                                    id="nameFirst" name="nameFirst">
                                                 </div>
                                             </div>
                                              <div class="col-xs-2">
                                                 <div class="form-group">
                                             <label for="classIdFirst">所属班级</label>
                                            <input type="hidden" id="classIdFirst"
                                                   name="classIdFirst"
                                                   value="">
                                            <input type="text" class="form-control" id="classIdFirstSearch"
                                                  name="classIdFirstSearch"  placeholder="请输入关键词查询班级"
                                                         value="">
                                                 </div>
                                             </div>                                             <div class="col-xs-2">
                                                 <div class="form-group">
                                                 <label for="sexFirst">性别</label>
                                                     <select class="form-control" id="sexFirst" name="sexFirst">
                                                                 <option value="">全部</option>
                                                                 <option value="0"
                                                                     <c:if test="${ sex == 0 }">selected</c:if>
                                                                  >类型一
                                                                 </option>
                                                                 <option value="1"
                                                                     <c:if test="${ sex == 1 }">selected</c:if>
                                                                  >类型二
                                                                 </option>
                                                     </select>
                                                 </div>
                                             </div>                                              <div class="col-xs-2">
                                                 <div class="form-group">
                                             <label for="adminUserIdFirst">管理员</label>
                                            <input type="hidden" id="adminUserIdFirst"
                                                   name="adminUserIdFirst"
                                                   value="">
                                            <input type="text" class="form-control" id="adminUserIdFirstSearch"
                                                  name="adminUserIdFirstSearch"  placeholder="请输入关键词查询管理员表"
                                                         value="">
                                                 </div>
                                             </div>                                                <div class="col-xs-2">
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
                            <div class="row">
                                <div class="form-horizontal" >
                                    <div class="col-sm-1">
                                        <input type="button" onclick="batchDeleteItem('student')" value="删除选中项">
                                    </div>
                                </div>
                                <div class="form-horizontal" >
                                    <div class="col-sm-1">
                                        <input type="button" class="form-control" onclick="truncateTable('student')" value="清空数据">
                                    </div>
                                </div>
                                <div class="form-horizontal" >
                                    <label class="col-sm-1 control-label">导出Json文件:</label>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" size="2"  id="start" value="0">
                                    </div>
                                    <div class="col-sm-1">
                                        <input type="text"  class="form-control" size="2" id="limit" value="50">
                                    </div>
                                    <div class="col-sm-1">
                                        <input type="button" class="form-control" onclick="exportJsonFile()" value="开始导出">
                                    </div>
                                </div>
                                <div class="form-horizontal" >
                                    <label class="col-sm-1 control-label">导入Json文件:</label>
                                    <div class="col-sm-1">
                                        <input type="file" id="importJsonFile" class="form-control"
                                               name="myfiles" onchange="importJsonFile('admin/student/import/json')"/>
                                    </div>
                                </div>
                            </div>
                            <table id="example2" class="table table-bordered table-hover dataTable">
                                <thead id="thead">
                                              <tr>
                                                  <th><input type="checkbox" id="checkAll"></th>
                                                  <th>序号</th>
                                                 <th  class="sorting" orderField="name">                                                      学生名称
                                                  </th>
                                                 <th  class="sorting" orderField="class_id">                                                      所属班级
                                                  </th>
                                                 <th  class="sorting" orderField="sex">                                                      性别
                                                  </th>
                                                 <th  class="sorting" orderField="admin_user_id">                                                      管理员
                                                  </th>
                                                  <th>操作</th>
                                              </tr>
                                </thead>
                                <tbody id="tbody"></tbody>
                                <tfoot></tfoot>
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

<script type="text/javascript" src="assets/admin/js/ajaxfileupload.js"></script>
<script charset="utf-8" src="assets/admin/app/base.js"></script>
<script language="javascript" type="text/javascript"
        src="${CTX}assets/admin/js/My97DatePicker/WdatePicker.js"></script>
<script src="assets/admin/app/list_page.js" type="text/javascript"></script>
<script src="assets/admin/app/student/studentPaging.js" type="text/javascript"></script>
<script type="text/javascript" >
    $(function(){
        //base.js
            foreignSearch('classes','classIdFirst','','id');
            foreignSearch('admin_user','adminUserIdFirst','','id');
    })
</script>
</body>
</html>

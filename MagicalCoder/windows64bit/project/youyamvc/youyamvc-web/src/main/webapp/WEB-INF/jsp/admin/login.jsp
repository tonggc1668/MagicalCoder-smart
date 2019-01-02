<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html class="bg-black">
<head>
    <base href="${CTX}">
    <%@include file="../common/head.jsp"%>
    <title>AdminLTE | Log in</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>

    <%@include file="include/head.jsp"%>

</head>
<body class="bg-black">

<div class="form-box" id="login-box">
    <div class="header">登录</div>
    <form action="admin" method="post">
        <div class="body bg-gray">
            <div class="form-group">
                <input type="text" name="userName" class="form-control" placeholder="用户名" value="${userName}"/>
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="密码" value="${password}"/>
            </div>
            <div class="form-group">
                <input type="text" name="checkCode" class="form-control" placeholder="验证码" value=""/>
            </div>
            <div class="form-group">
                <img alt="验证码看不清，换一张" src="admin/checkCode" id="checkCode" onclick="changeImg(this)">
                <span class="alert-danger">${alertMsg}</span>
            </div>
        </div>
        <div class="footer">
            <button type="submit" class="btn bg-olive btn-block">登录</button>
        </div>
    </form>
</div>
<%@include file="include/tail.jsp"%>
<script type="text/javascript">
    //刷新验证码
    function changeImg(obj){
        document.getElementById(obj.id).src="admin/checkCode?d="+Math.random();
    }
</script>
</body>
</html>
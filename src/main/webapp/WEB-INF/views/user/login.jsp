<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/nav.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-fire"></i> 登录</span>
        </div>

        <c:choose>
            <c:when test="${param.state == '1002'}">
                <div class="alert alert-success">您已安全退出</div>
            </c:when>
            <c:when test="${param.state == '1003'}">
                <div class="alert alert-success">密码设置成功，请重新登录</div>
            </c:when>
        </c:choose>

        <form action="" class="form-horizontal" id="loginForm">

            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="username" id="username" autocomplete="off">
                    <%--autocomplete="off"帐号框不能记忆--%>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>
                <div class="controls">
                    <a href="/forget/password.do">忘记密码</a>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-primary" id="loginBtn">登录</button>
                <span id="errorMsg" class="text-error hide">账号或密码输入错误，请重新输入</span>
                <a class="pull-right" href="/reg.do">注册账号</a>
            </div>

        </form>
    </div>
    <!--box end-->
</div>
<!--container end-->

<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function(){
        $("#loginBtn").click(function(){
            $("#loginForm").submit();
        });
        $("#loginForm").validate({
           errorClass:'text-error',
           errorElement:'span',
           rules:{
               username:{
                   required:true
               },
               password:{
                   required:true
               }
           },
           message:{
               username:{
                   required:"请输入帐号"
               },
               password:{
                   required:"请输入密码"
               }
           },
           submitHandler:function(form){
               var $btn = $("#loginBtn");
               $.ajax({
                   url:"/login.do",
                   type:"post",
                   data:$(form).serialize(),
                   beforeSend:function(){
                       //$btn.attr("class","fa fa-modx fa-spin");
                       $btn.text("登录中。。。").attr("disabled","disabled");
                   },
                   success:function(json){
                        if(json.state == "error") {
                            $("#errorMsg").text(json.message);
                        } else {
                            $("#errorMsg").show();
                            window.location.href = "/index.do";
                        }
                   },
                   error:function(){},
                   complete:function(){
                       $("#errorMsg").show().fadeOut(5000);
                       $btn.text("登录").removeAttr("disabled");
                   }
               })
           }
       });


    })
</script>
</body>
</html>

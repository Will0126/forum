<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设置新密码</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/nav.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-fire"></i> 设置新密码</span>
        </div>
        <form action="" class="form-horizontal" id="passwordForm">
            <div class="control-group">
                <div class="controls">
                    <span id="errorMsg"></span>
                </div>
            </div>
            <input type="hidden" name="token" value="${token}">
            <div class="control-group">
                <label class="control-label">新密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">确认密码</label>
                <div class="controls">
                    <input type="password" name="repassword" >
                </div>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-primary" id="setBtn">设置新密码</button>
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
        $("#setBtn").click(function(){
            $("#passwordForm").submit();
        });
        $("#passwordForm").validate({
            errorClass:'text-error',
            errorelement:'span',
            rules:{
                password:{
                    required:true,
                    rangelength:[6,18]
                },
                repassword:{
                    required:true,
                    equalTo:'#password'
                }
            },
            message:{
                password:{
                    required:"请输入密码",
                    rangelength:"密码长度6~18位"
                },
                repassword:{
                    required:"请再次输入密码",
                    equalTo:"两次密码不一致，请重新输入"
                }
            },
            submitHandler:function(){
                var $btn = $("#setBtn");
                $.ajax({
                    url:"/forgetpassword/setpassword.do",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function(){
                        $btn.text("设置中。。。").attr("disabled","disabled");
                    },
                    success:function(json){
                        if(json.state == "error") {
                            $("#errorMsg").text(json.message);
                        } else {
                            //非空
                            window.location.href = "/login.do?state=1003";
                        }
                    },
                    error:function(){
                        $("#errorMsg").text("服务器异常，请稍后再试");
                    },
                    complete:function(){
                        $btn.text("设置").removeAttr("disabled");

                        //当输入错误，提醒帐号密码错误，当点击帐号和密码框，清空提示
                        $("#username").click(function(){
                            $("#errorMsg").text("").removeAttr("class","text-error");
                        });
                        $("#password").click(function(){
                            $("#errorMsg").text("").removeAttr("class","text-error");
                        });
                    }


                });
            }
        });
    })
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册用户</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-fire"></i> 注册账号</span>
        </div>

        <form class="form-horizontal" id="regForm">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="username">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" name="email">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">验证码</label>
                <div class="controls">
                    <input type="text" name="code">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>
                    <div class="controls">
                        <a href="javascript:;" id="change"><img src="/patchca.png" id="patchca"></a>

                    </div>
            </div>
            <div class="form-actions">
                <button type="button" id="regBtn" class="btn btn-primary">注册</button>
                <span id="regMsg" class="hide">注册成功，<span class="sec">3</span>秒后自动跳转到登录页面</span>
                <a class="pull-right" href="/login.do">登录</a>
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

        $("#regBtn").click(function(){
            $("#regForm").submit();
        });

        //点击更换验证码
        $("#change").click(function() {
            $("#patchca").attr("src","/patchca.png?XXX=" + new Date().getTime().toString());
        });

        $("#regForm").validate({
            //表单验证配置
            //发生时错误 Class属性为text-error
            errorClass: 'text-error',
            //发生时错误 Element属性产生一个span标签，默认为label
            errorElement: 'span',
            //规则
            rules: {
                username: {
                    required: true,//必填项
                    minlength: 3,//长度最少3字符
                    maxlength: 10,//长度最多10字符
                    remote:"/validate/username.do"
                    //当失去焦点，以get形式，将表单值get到设置路径，进行远程验证
                    //当返回值为true时能用，false不能
                },
                password: {
                    required: true,//必填项
                    rangelength: [6, 18]//密码长度6-18位
                },
                repassword: {
                    required: true,//必填项
                    equalTo: '#password'//要求与上面密码一致
                },
                email: {
                    required: true,//必填项
                    email: true,//必须为邮箱格式
                    remote:"/validate/email.do"
                },
                code:{
                    required:true,
                    remote:"/validate/patchca.do"
                }
            },
            //消息
            messages: {
                username: {
                    required: "请输入帐号",
                    minlength: "账号长度至少为三个字符",
                    maxlength: "账号长度至少为十个字符",
                    remote:"该帐号已被占有，请重新输入"
                },
                password: {
                    required: "请输入密码",
                    rangelength: "密码长度为6~18位"
                },
                repassword: {
                    required: "请再次输入密码",
                    equalTo: "两次输入的密码不一致，请重新输入"
                },
                email: {
                    required: "请输入邮箱",
                    email: "邮箱格式不正确，请重新输入",
                    remote:"该邮箱已被注册，请重登录"
                },
                code:{
                    required:"请输入验证码",
                    remote:"验证码输入错误，请重新输入"
                }
            },
            //当以上验证都通过并且提交时会触发
            submitHandler: function (form) {
               //form为整个表单元素
                //Ajax提交 参数为表单输入的帐号密码邮箱
                //$(form).serialize() 表单元素.serialize(),可以自动拼好表单的键值
                $.post("/reg.do",$(form).serialize())
                        .done(function(result){
                        //提交成功时,会返回一个结果
                            if(result.state == "error"){
                                alert(result.messages)
                            } else {
                                //注册成功
                                //提示3秒后自动跳转
                                //$("#regMsg").show();
                                $("#regMsg").removeClass("hide");
                                //.show()显示隐藏
                                var sec = 3;
                                setInterval(function(){
                                    sec--;
                                    if(sec == 0){
                                        window.location.href = "/login.do"
                                    }
                                    $(".sec").text(sec);
                                },1000)
                            }
                        }).fail(function(){
                        //提交失败时
                            alert("服务器异常，请稍候再试")
                });
            }
        });
    })
</script>
</body>
</html>
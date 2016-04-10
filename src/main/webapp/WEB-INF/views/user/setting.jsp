<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户资料设置</title>
    <link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/nav.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>
        <form id="emailForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" value="${sessionScope.curr_user.username}" readonly>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" value="${sessionScope.curr_user.email}" id="email" name="email" placeholder="${sessionScope.curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="emailBtn" class="btn btn-primary">保存</button>
                <span id="emailEMsg" class="text-error hide">服务器忙，请稍候再试</span>
                <span id="emailSMsg" class="text-success hide">设置成功</span>

            </div>
        </form>
    </div>
    <!--email end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form class="form-horizontal" id="passwordForm">
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
            <div class="form-actions">
                <button type="button" id="passwordBtn" class="btn btn-primary">保存</button>
                <span id="passwordEMsg" class="text-error hide">服务器忙，请稍候再试</span>
                <span id="passwordSMsg" class="text-success hide">设置成功</span>
            </div>

        </form>

    </div>
    <!--password end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img src="${sessionScope.curr_user.avatar}?imageView2/1/w/40/h/40" class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <button class="btn btn-primary">上传新头像</button>
                <span id="pngEMsg" class="text-error hide">服务器忙，请稍候再试</span>
                <span id="pngSMsg" class="text-success hide">设置成功</span>
            </div>


            <%--内存地址，查询出来的new出来的和存储到session的user指的同一片内存空间，一旦修改查询的user，session空间内的会自动修改--%>
        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function(){
        //修改邮箱
        $("#emailBtn").click(function(){
            $("#emailForm").submit();
        });

        $("#emailForm").validate({
            errorClass:'text-error',
            errorElement:'span',
            rules:{
                email:{
                    //需要实现输入框内清空，在输入框显示默认值
                    required:true,
                    remote:"/validate/email.do?from=setChangeEmail"
                }
            },
            messages:{
                email:{
                    required:"请输入邮箱",
                    remote:"邮箱已被占有，请重新输入"
                }
            },
            submitHandler:function(){
                var $ebtn = $("#emailBtn");
                $.ajax({
                    url:"/editUser.do",
                    type:"post",
                    data:$("#emailForm").serialize(),
                    beforeSend:function(){
                        $ebtn.attr("disabled","disabled").text("设置中。。。");
                    },
                    success:function(json){
                        if(json.state == "error"){
                            $("#emailEMsg").text(json.message).show().fadeOut(8000);
                        } else {
                            $("#emailSMsg").show().fadeOut(8000);
                            $("#font").show().fadeOut(8000);
                        }
                    },
                    error:function(){
                        $("#emailEMsg").show().fadeOut(8000);
                    },
                    complete:function(){
                        $ebtn.removeAttr("disabled","disabled").text("保存");
                    }
                })
            }
        });


        //修改邮箱 end

        //修改密码
        $("#passwordBtn").click(function(){
           $("#passwordForm").submit();
        });

        $("#passwordForm").validate({
            errorClass:"text-error",
            errorElement:"span",
            rules:{
                password:{
                    required: true,//必填项
                    rangelength: [6, 18]//密码长度6-18位
                },
                repassword:{
                    required: true,//必填项
                    equalTo: '#password'//要求与上面密码一致
                }
            },
            messages:{
                password:{
                    required:"请输入密码",
                    ranglenth:"密码长度限制6~18位"
                },
                repassword:{
                    required:"请再次输入密码",
                    equalTo:"两次密码不一致，请重新输入"
                }
            },
            submitHandler:function(){
                var $ebtn = $("#passwordBtn");
                $.ajax({
                    url:"/editUser.do",
                    type:"post",
                    data:$("#passwordForm").serialize(),
                    beforeSend:function(){
                        $ebtn.attr("disabled","disabled").text("设置中。。。");
                    },
                    success:function(json){
                        if(json.state == "error"){
                            $("#passwordEMsg").text(json.message).show().fadeOut(8000);
                        } else {
                            $("#passwordSMsg").text("设置成功，请重新登录").show();
                            $("#font").show();
                            setTimeout(function(){
                                window.location.href="/login.do?redirecturl=/setting.do";
                            },3000);
                        }
                    },
                    error:function(){
                        $("#emailMsg").show().fadeOut(8000);
                    },
                    complete:function(){
                        $ebtn.removeAttr("disabled","disabled").text("保存");
                    }
                })
            }
        });
        //修改密码  end

        //上传/修改头像

    })
</script>
</body>
</html>

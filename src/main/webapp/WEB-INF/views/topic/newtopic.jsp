<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form id="topicForm" style="padding: 20px">
            <label class="control-label">主题标题</label>
            <input type="text" name="title" style="width: 100%;box-sizing: border-box;height: 30px" placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <span id="SMsg" class="text-success hide">发布成功</span>
            <span id="EMsg" class="text-error hide">服务器异常，请稍候</span>
            <label class="control-label">正文</label>
            <textarea name="context" id="editor"></textarea>

            <select name="nodeid" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                    <option value="${node.id}">${node.nodename}</option>
                    <%--value定义发往服务器的值，如果不写则为文本内容--%>
                </c:forEach>
            </select>
        </form>
        <%--当button脱离form，就只是一个button--%>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn" class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor')
            //optional options
        });

        $("#sendBtn").click(function(){
            $("#topicForm").submit();
        });

        $("#topicForm").validate({
            errorClass:'text-error',
            errorElement:'span',
            rules:{
                title:{
                    required:true,
                    maxlength:200
                },
                nodeid:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:"请输入主题",
                    maxlength:"主题内容不能超过200字"
                },
                nodeid:{
                    required:"请选择节点"
                }
            },

            submitHandler:function(form){
                var $btn = $("#sendBtn");
                $.ajax({
                    url:"/topic/new.do",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function(){
                        $btn.attr("disabled","disabled").text("发布中...");
                    },
                    success:function(json){
                        if(json.state == "success"){
                            window.location.href = "/topic/view.do?id="+json.data;
                        }
                    },
                    error:function(){
                        $("#EMsg").show().fadeOut(5000);
                    },
                    complete:function(){
                        $btn.removeAttr("disabled","disabled").text("发布主题");
                    }
                });
            }
        });


    });
</script>

</body>
</html>
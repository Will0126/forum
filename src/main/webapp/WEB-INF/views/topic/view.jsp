<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <%--代码高亮highlight实现第一步--%>
    <link rel="stylesheet" href="/static/js/highlight/styles/github-gist.css">
    <link rel="stylesheet" href="/static/js/googleCode/tomorrow-night-bright.css">
    <%--代码高亮code实现第二步--%>
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
        /*解决代码bootstrap造成的代码高亮highlight出现边框和背景色*/
        pre{
            padding: 0px;
            border: none;
            background-color: transparent;
        }
    </style>
</head>
<body>
<%@include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/index.do">首页</a> <span class="divider">/</span></li>
            <li class="active">${topic.node.nodename}</li><%--topic.node == topic.getNode()--%>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="${topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${topic.title}</h3>
            <p class="topic-msg muted"><a href="">${topic.user.username}</a> · <span class="timeago" title="${topic.createtime}"></span></p>
        </div>
        <div class="topic-body">
            ${topic.context}
        </div>
        <div class="topic-toolbar">
            <c:if test="${not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>
                        <c:when test="${action == 'fav'}">
                            <li><a href="javascript:;" class="fav">取消收藏</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" class="fav">加入收藏</a></li>
                        </c:otherwise>
                    </c:choose>
                    <li><a href="javascript:;" class="like">感谢</a></li>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.clicknum}次点击</li>
                <li id="favNum">${topic.favnum}人收藏</li>
                <li>${topic.thanknum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            <span id="replyNum"></span>个回复| 直到<span id="replyTime"></span>
        </div>
        <div id="comment-List"></div>
    </div>
    <c:choose>
     <c:when test="${not empty sessionScope.curr_user}">
         <div class="box" style="margin:20px 0px;">
             <div id="notConmmentValue" class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
             <form id="commentForm" style="padding: 15px;margin-bottom:0;">
                 <input type="hidden" id="topicid" value="${topic.id}">
                 <textarea name="context" id="editor"></textarea>
             </form>
             <a name="new"></a>
             <div class="talk-item muted" style="text-align: right;font-size: 12px">
                 <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                 <button id="sendComment" class="btn btn-primary">发布</button>
             </div>
         </div>
     </c:when>
     <c:otherwise>
        <div class="box" style="margin:20px 0;">
         <div class="talk-item">
             请<a href="/login.do?redirecturl=/topic/view.do?id=${topic.id}">登录</a>后再进行回复
         </div>
     </c:otherwise>
    </c:choose>

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="http://cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="http://cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<%--语言包zh-cn--%>
<script src="/static/js/highlight/highlight.pack.js"></script>
<script src="/static/js/timeago.js"></script>
<%--highlight包-highlight.pack.js，代码高亮highlight实现第二步--%>
<script src="/static/js/googleCode/prettify.js/"></script>
<%--googlecode代码高亮第一步--%>
<%--模版--%>
<script src="/static/js/handlebars-v4.0.5.js"></script>
<script type="text/mytemplate" id="commentListTemplate">
    {{#each data}}
    <div class="talk-item">
        <table class="talk-table">
            <tr>
                <td width="50">
                    <img class="avatar" src="{{user.avatar}}?imageView2/1/w/40/h/40" alt="">
                </td>
                <td width="auto">
                    <a href="" style="font-size: 12px">{{user.username}}</a> <span style="font-size: 12px" class="reply timeago" title="{{createtime}}"></span>
                    <br>
                    {{{comment}}}
                </td>
                <td width="70" align="right" style="font-size: 12px">
                    <a href="javascript:;" class="replyLink" data-count="{{counter @index}}" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                    <span class="badge">{{counter @index}}</span>
                    </td>
            </tr>
        </table>
        <a name="reply{{counter @index}}"></a>
    </div>
    {{/each}}
</script>
<script>
    $(function(){
        //一定要搞定这段代码
        <c:if test="${sessionScope.curr_user}">
            //只有当登录再运行当前这段代码，否则无法获取textarea会出错
            var editor = new Simditor({
                textarea: $('#editor'),
                toolbar:false,
                //不显示其他功能，可以考虑添加上传图片能力
                //optional options

                //编辑框内的图片上传
                upload:{
                    url:"http://uoload.qiniu.com",
                    filekey:"file",
                    params:{"token":"${uploadToken}"}
                }
            });
        //回复提交，需要登录
        $("#sendComment").click(function() {
            //获取评论框内输入内容
            var value = editor.getValue();
            if (value) {
                sendComment(value);
            } else {
                //建议聚焦到输入框，然后框内显示请输入评论内容
                editor.setValue("请输入评论内容").fadeIn(2000).fadeOut(2000).setValue("");
                editor.focus();
            }
        })
        </c:if>

        //时间显示  1、
        $(".timeago").timeago();
        //2、moment.js


        //代码高亮highlight实现第三步
         hljs.initHighlightingOnLoad();

        //代码高亮code实现第三步
        //$("pre").addClass("prettyPrint");
        //prettyPrint();


        Handlebars.registerHelper("counter",function(index){
            return index + 1;
        });

        //异步查询当前帖子的所有评论
        function initComment(){
            $.ajax({
                url:"/topic/comment/load.do",
                type:"post",
                data:{"topicid","${topic.id}"},
                beforeSend:function(){

                },
                success:function(json){
                  if(json.state == "error"){
                      editor.setValue(json.message).fadeIn(2000).fadeOut(2000).setValue("");
                  } else {
                      //获取自定义模版内容
                      var source = $("#commentListTemplatec").html();
                      //编译成模版
                      var template = Handlebars.compile(source);
                      //传入参数，转成html
                      var html = template(json);
                      $("#comment-List").html("").append(html);

                      $("#replyNum").text(json.data.length);
                      if(json.data.length != 0){
                          $("#replyTime").text(json.data[json.data.length - 1].createtime);
                      } else {
                          $("#replyTime").text(moment().format("YYYY-MM-DD HH:mm:ss"));
                          $(".timeago").timeago();
                      }
                  }
                },
                error:function(){
                    editor.setValue("服务器异常，请稍候").fadeIn(2000).fadeOut(2000).setValue("");
                },
                complete:function(){

                }

            });
        }

        function sendComment(){
            //get请求不但会显示在地址栏，而且字符长度有限，低版本浏览器最多255个字符
            var $btn = $("#sendComment");
            $.ajax({
                url:"/topic/comment/new.do",
                type:"post",
                date:{"comment":value, "topicid":"${topic.id}"},
                beforeSend:function(){
                    $btn.attr("disabled","disabled").text("发布中...");
                },
                success:function(json){
                    if(json == "error"){
                        editor.setValue(json.message).fadeIn(2000).fadeOut(2000).setValue(" ");
                    } else {
                        initComment();
                        editor.setValue(" ");

                    }
                },
                error:function(){
                    editor.setValue("服务器异常，请稍候").fadeIn(2000).fadeOut(2000).setValue(" ");
                },
                complete:function(){
                    $btn.removeAttr("disabled").text("发布");

                }

            })
        }

        initComment();

        //点击评论内容中的回复评论的超链
        //动态创造出的元素，无法绑点任何事件，
        // 方法 1、需要通过代理机制绑点
        //$(要代理的元素的父类元素).delegate("要代理元素","代理事件",function(){XXX})
        $(document).delegate(".replyLink","click",function(){

            var counter = $(this).attr("data-count");
            var msg = "<a href= '#reply"+counter+"'>#" + counter + "</a>&nbsp;&nbsp;";
            editor.setValue(msg);
            editor.focus();
            window.location.href="#new";
        });

        /*
        // 方法 2、需要通过on绑点
        //$(要代理的元素的父类元素).on("代理事件","要代理元素",function(){XXX})
        $(document).on("click",".replyLink",function(){
            var counter = $(this).attr("data-count");
            editor.setValue(counter);
        });
        */

        //加入或取消收藏
        $(".fav").click(function(){
            var $this = $(this);
            var action = $this.text() == "加入收藏" ? 'fav' : 'unfav';
            $.post("/topic/fav.do",{"topicid":"${topic.id}","action":"action"})
                    .done(function(result){
                        if(result.state == "error"){
                            alert(result.message);
                        } else {
                            if(action == "fav"){
                                $("#favNum").text($("#favNum").text() + 1);
                                $this.text("取消收藏");
                            } else {
                                $this.text("加入收藏");
                            }
                        }
                    })
                    .fail(function(){
                alert("服务器忙，请稍后再试");
            });
        });

    });
</script>

</body>
</html>
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
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
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
            <p class="topic-msg muted"><a href="">${topic.user.username}</a> · <span id="topicTime">${topic.createtime}</span></p>
        </div>
        <div class="topic-body">
            ${topic.context}
        </div>
        <div class="topic-toolbar">
            <ul class="unstyled inline pull-left">
                <li><a href="">加入收藏</a></li>
                <li><a href="">感谢</a></li>
                <li><a href=""></a></li>
            </ul>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.clicknum}次点击</li>
                <li>${topic.favnum}人收藏</li>
                <li>${topic.thanknum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            9个回复 | 直到2015年12月25日 22:23:34
        </div>
        <div class="talk-item">
            <table class="talk-table">
                <tr>
                    <td width="50">
                        <img class="avatar" src="http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/40/h/40" alt="">
                    </td>
                    <td width="auto">
                        <a href="" style="font-size: 12px">fankay</a> <span style="font-size: 12px" class="reply">4小时前</span>
                        <br>
                        <p style="font-size: 14px">不知道国内有哪些公司开始用 react-native 了呢？我就知道天猫 Pad 版部分</p>
                    </td>
                    <td width="70" align="right" style="font-size: 12px">
                        <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                        <span class="badge">1</span>
                    </td>
                </tr>
            </table>
        </div>

        <div class="talk-item">
            <table class="talk-table">
                <tr>
                    <td width="50">
                        <img class="avatar" src="http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/40/h/40" alt="">
                    </td>
                    <td width="auto">
                        <a href="" style="font-size: 12px">fankay</a> <span style="font-size: 12px" class="reply">4小时前</span>
                        <br>
                        <p style="font-size: 14px">不知道国内有哪些公司开始用 react-native 了呢？我就知道天猫 Pad 版部分</p>
                    </td>
                    <td width="70" align="right" style="font-size: 12px">
                        <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                        <span class="badge">2</span>
                    </td>
                </tr>
            </table>
        </div>

        <div class="talk-item">
            <table class="talk-table">
                <tr>
                    <td width="50">
                        <img class="avatar" src="http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/40/h/40" alt="">
                    </td>
                    <td width="auto">
                        <a href="" style="font-size: 12px">fankay</a> <span style="font-size: 12px" class="reply">4小时前</span>
                        <br>
                        <p style="font-size: 14px">不知道国内有哪些公司开始用 react-native 了呢？我就知道天猫 Pad 版部分</p>
                    </td>
                    <td width="70" align="right" style="font-size: 12px">
                        <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                        <span class="badge">3</span>
                    </td>
                </tr>
            </table>
        </div>

        <div class="talk-item">
            <table class="talk-table">
                <tr>
                    <td width="50">
                        <img class="avatar" src="http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/40/h/40" alt="">
                    </td>
                    <td width="auto">
                        <a href="" style="font-size: 12px">fankay</a> <span style="font-size: 12px" class="reply">4小时前</span>
                        <br>
                        <p style="font-size: 14px">不知道国内有哪些公司开始用 react-native 了呢？我就知道天猫 Pad 版部分</p>
                    </td>
                    <td width="70" align="right" style="font-size: 12px">
                        <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                        <span class="badge">4</span>
                    </td>
                </tr>
            </table>
        </div>

        <div class="talk-item">
            <table class="talk-table">
                <tr>
                    <td width="50">
                        <img class="avatar" src="http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/40/h/40" alt="">
                    </td>
                    <td width="auto">
                        <a href="" style="font-size: 12px">fankay</a> <span style="font-size: 12px" class="reply">4小时前</span>
                        <br>
                        <p style="font-size: 14px">不知道国内有哪些公司开始用 react-native 了呢？我就知道天猫 Pad 版部分</p>
                    </td>
                    <td width="70" align="right" style="font-size: 12px">
                        <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                        <span class="badge">50</span>
                    </td>
                </tr>
            </table>
        </div>

    </div>

    <div class="box" style="margin:20px 0px;">
        <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
        <form action="" style="padding: 15px;margin-bottom:0px;">
            <textarea name="" id="editor"></textarea>
        </form>
        <div class="talk-item muted" style="text-align: right;font-size: 12px">
            <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
            <button class="btn btn-primary">发布</button>
        </div>
    </div>

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
<script>
    $(function(){
        //一定要搞定这段代码
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:false
            //optional options
        });
        //moment(int 时间戳).formNow()将时间戳转化为相对时间
        $("#topicTime").text(moment(parseInt($("#topicTime").text())).fromNow());

    });
</script>

</body>
</html>
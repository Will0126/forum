<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header-bar">
    <div class="container">
        <a href="/index.do" class="brand">
            <i class="fa fa-leaf"></i>
            <%--<i class="fa fa-arrow-up fa-spin"></i>--%>
            <%--<i class="fa fa-arrow-right fa-spin"></i>--%>
            <%--<i class="fa fa-arrow-down fa-spin"></i>--%>
            <%--<i class="fa fa-arrow-left fa-spin"></i>--%>
            <%--左侧图标 fa为制式，可随意更换，例如icon等等，样式为fa-XXX spin为旋转     --%>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.curr_user}">
                    <li>
                        <a href="#">
                            <%--(域名+key)+裁剪--%>
                            <img src="${sessionScope.curr_user.avatar}?imageView2/1/w/20/h/20" class="img-circle" alt="">
                        </a>
                    </li>
                    <li>
                        <a href=""><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bell"></i></a>
                    </li>
                    <li>
                        <a href="setting.html"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/logout.do "><i class="fa fa-sign-out"></i></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/login.do"><i class="fa fa-sign-in"></i></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<!--header-bar end-->
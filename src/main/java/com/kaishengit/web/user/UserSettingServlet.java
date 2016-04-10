package com.kaishengit.web.user;

import com.kaishengit.util.ConfigProp;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/setting.do")
public class UserSettingServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //产生七牛云上传Token
        //1.创建Auth对象
        Auth auth = Auth.create(ConfigProp.get("qiniu.ak"),ConfigProp.get("qiniu.sk"));
        //2.指定上传空间，获取上传token
        String token = auth.uploadToken(ConfigProp.get("qiniu.buckname"));

        request.setAttribute("token",token);

        forward(request,response,"user/setting");
    }
}

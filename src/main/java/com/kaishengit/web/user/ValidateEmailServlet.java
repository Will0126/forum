package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/validate/email.do")
public class ValidateEmailServlet extends BaseServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //为解决get提交中文
        String email = req.getParameter("email");
        email = new String(email.getBytes("ISO8859-1"),"UTF-8");

        UserService userService = new UserService();
        User euser = userService.findByEmail(email);
        User suser = (User) req.getSession().getAttribute("curr_user");

        String from = req.getParameter("from");

        String result;
        if(euser == null) {
            result = "true";
        } else {
            //通过判断邮箱验证是否来自设置界面修改，可以实现用户重新注册自己的邮箱
            if("regEmail".equals(from)) {
                result = "false";
            } else if(suser.getEmail().equals(euser.getEmail()) && "setChangeEmail".equals(from)){
                result = "true";
            } else {
                result = "false";
            }
        }
        rendText(resp,result);
    }


}



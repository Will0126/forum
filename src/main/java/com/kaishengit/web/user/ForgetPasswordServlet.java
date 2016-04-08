package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forget/password.do")
public class ForgetPasswordServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        forward(request,response,"user/forget");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        UserService userService = new UserService();
        userService.forgetPassword(username);

        resp.sendRedirect("/forget/password.do?state=1001");
    }
}

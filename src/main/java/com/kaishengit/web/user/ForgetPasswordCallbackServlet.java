package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forget/callback.do")
public class ForgetPasswordCallbackServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if(StringUtils.isEmpty(token)){
            //没有token，400错误 bed in request
            response.sendError(400);
        } else {
            UserService userService = new UserService();
            try {
                request.setAttribute("token",token);
                forward(request,response,"user/newpassword");
                //
            } catch (Exception e) {
                request.setAttribute("massage",e.getMessage());
                forward(request,response,"user/tokenerror");
            }

        }


    }
}

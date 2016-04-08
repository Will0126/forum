package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/forgetpassword/setpassword.do")
public class ForgetPasswordSetPasswordServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Map<String,Object> result = Maps.newHashMap();
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(password)){
            result.put("state","error");
            result.put("message","参数错误");
        } else {
            UserService userService = new UserService();
            try {
                userService.forgetPasswordSetNewPassword(password,token);
                result.put("state","success");
            } catch (Exception e){
                result.put("state","error");
                result.put("message",e.getMessage());
            }
        }

        rendJson(response,result);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

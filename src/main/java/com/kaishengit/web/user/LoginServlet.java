package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
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

@WebServlet("/login.do")
public class LoginServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req,resp,"user/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Map<String,Object> result = Maps.newHashMap();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            result.put("state","error");
            result.put("message","参数错误");
        } else {

            //String ip = req.getRemoteAddr();//获取登录用户的ip地址
            UserService userService = new UserService();
            User user = userService.login(username,password,getRemoteIp(req));
            if(user == null){
                //user返回为空
                result.put("state","error");
                result.put("message","账号或密码输入错误");
            } else {
                req.getSession().setAttribute("curr_user",user);
                result.put("state","success");
            }
        }
        //返回一个Json
        rendJson(resp,result);

    }
}

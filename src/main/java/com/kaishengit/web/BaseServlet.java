package com.kaishengit.web;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 公共服务父类
 */
public class BaseServlet extends HttpServlet {

    public void forward(HttpServletRequest request, HttpServletResponse response, String viewsName) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/" + viewsName + ".jsp").forward(request,response);
    }

     public void rendJson(HttpServletResponse response, Object result) throws IOException {
         response.setContentType("application/json;charset=UTF-8");//设置响应头
         PrintWriter out = response.getWriter();
         //转化为json并输出
         out.print(new Gson().toJson(result));
         out.flush();
         out.close();
     }


    public void rendText(HttpServletResponse response, Object result) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");//设置响应头
        PrintWriter out = response.getWriter();
        //将字符串输出
        out.print(result);
        out.flush();
        out.close();
    }

}

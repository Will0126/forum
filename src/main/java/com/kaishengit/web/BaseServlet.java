package com.kaishengit.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 公共服务父类
 */
public class BaseServlet extends HttpServlet {

    public void forward(HttpServletRequest request, HttpServletResponse response, String viewsName) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/" + viewsName + ".jsp").forward(request,response);
    }

}

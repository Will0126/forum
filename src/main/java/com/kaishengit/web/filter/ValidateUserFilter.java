package com.kaishengit.web.filter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 判断用户是否登录的过滤器
 */
public class ValidateUserFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //首先获取用户请求的路径
        //request.getRequestURI()获取请求路径
        String path = request.getRequestURI();
        //startsWith（XXX）字符串以XXX开头
        if(path.equals("/topic/view.do")) {
            filterChain.doFilter(request,response);
        } else if(path.startsWith("/user/") || path.startsWith("/topic/")){
            //是则判断是否登录
            if(request.getSession().getAttribute("curr_user") == null){
                //未登录，打回去登录
                response.sendRedirect("/login.do?state=1001&redirecturl=" + path);
            } else {
                //已登录,放行
                filterChain.doFilter(request,response);
            }
        } else {
            //不是则放行
            filterChain.doFilter(request,response);
        }
    }
}

package com.kaishengit.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ExcodingFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //可以写在配置文件
        //请求时的中文提交问题
        servletRequest.setCharacterEncoding("UTF-8");
        //输出json时，无需再配置
        servletResponse.setCharacterEncoding("UTF-8");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}

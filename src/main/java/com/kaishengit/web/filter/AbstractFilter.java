package com.kaishengit.web.filter;

import javax.servlet.*;
import java.io.IOException;

public abstract class AbstractFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    //实现Filter接口，并且对init和destory方法进行空实现，还是抽象类
    @Override
    public void destroy() {}
}

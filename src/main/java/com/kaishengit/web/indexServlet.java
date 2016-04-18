package com.kaishengit.web;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index.do")
public class indexServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String node = req.getParameter("node");
        String pageNo = req.getParameter("pageNo");

        List<Node> nodeList = new TopicService().findAllNode();
        //分页
        Page<Topic> page = new TopicService().showIndeTopic(node,pageNo);
        req.setAttribute("nodeList",nodeList);
        req.setAttribute("page",page);
        forward(req,resp,"index");
    }
}

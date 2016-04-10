package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/topic/new.do")
public class NewTopicServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> result = Maps.newHashMap();
        if(isAjaxRequest(req)){
            String title = req.getParameter("title");
            String context = req.getParameter("context");
            Integer nodeid = Integer.valueOf(req.getParameter("nodeid"));
            Integer userid  = getLoginUser(req).getId();

            if(title != null){
                Topic topic = new TopicService().saveNewTopic(title,context,nodeid,userid);
                result.put("state","success");
                result.put("data",topic.getId());

            } else {
                result.put("state","error");
                result.put("message","标题不能为空");
            }
         }

        rendJson(resp,result);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Node> nodeList = new TopicService().findAllNode();
        req.setAttribute("nodeList",nodeList);
        forward(req,resp,"topic/newtopic");
    }
}

package com.kaishengit.web.topic;

import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/topic/topic.do")
public class TopicServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (StringUtils.isNumeric(id)){
            Topic topic = new TopicService().findTopicById(id);
            if (topic != null){
                req.setAttribute("topic",topic);
                forward(req,resp,"topic/topic");

            } else {
                resp.sendError(404);
            }
        } else {
            resp.sendError(404);
        }
    }


}

package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/topic/fav.do")
public class FavTopicServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> result = Maps.newHashMap();
        TopicService topicService = new TopicService();
        String topicid = req.getParameter("topicid");
        String action = req.getParameter("action");
        User user = getLoginUser(req);

        if(isAjaxRequest(req)){
            if ((!"fav".equals(action) && !"unfav".equals(action)) || !StringUtils.isNumeric(topicid) || user == null) {
                 result.put("state","error");
                 result.put("message","参数错误");
            } else {
                Topic topic = topicService.findTopicById(topicid);
                if(topic == null){
                    result.put("state","error");
                    result.put("message","收藏错误");
                } else {
                    try {
                        topicService.favTopic(topic,user,action);
                        result.put("state","success");
                    } catch (ServiceException ex){
                        result.put("state","error");
                        result.put("message",ex.getMessage());
                    }
                }
            }
            rendJson(resp,result);
        }

    }
}

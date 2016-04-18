package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Comment;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/topic/Comment/new.do")
public class NewCommentServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> result = Maps.newHashMap();
        String commentContext = request.getParameter("comment");
        String topicid = request.getParameter("topicid");
        User user = getLoginUser(request);
        if(isAjaxRequest(request)){
            if(user == null || isAjaxRequest(request) || !StringUtils.isNumeric(topicid)){
                if (StringUtils.isEmpty(commentContext)){
                    result.put("state","error");
                    result.put("message","参数错误");
                } else {
                    Topic topic = new TopicService().findTopicById(topicid);

                    if(topic == null) {
                        result.put("state","error");
                        result.put("message","参数错误");
                    } else {
                        Comment comment = new TopicService().saveNewComment(commentContext,topic,user);
                        result.put("state","success");
                        result.put("data",comment);
                    }
                }
            }
            rendJson(response,result);
        } else {
            throw new RuntimeException("参数错误");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

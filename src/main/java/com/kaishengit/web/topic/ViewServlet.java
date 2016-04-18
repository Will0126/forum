package com.kaishengit.web.topic;

import com.kaishengit.entity.Fav;
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

@WebServlet("/topic/view.do")
public class ViewServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (StringUtils.isNumeric(id)) {
            Topic topic = new TopicService().viewTopic(id);
            if (topic != null){
                req.setAttribute("topic",topic);

                //判断当前登录用户是否收藏过该主题
                User user = getLoginUser(req);
                if(user != null) {
                    Fav fav = new TopicService().findFavTopicIdByAndUserId(topic.getId(),user.getId());
                    if(fav != null){
                        req.setAttribute("action","fav");
                    }
                }


                forward(req,resp,"topic/view");
            } else {
                resp.sendRedirect("/index.do?state=1101");
            }
        } else{
            resp.sendRedirect("/index.do");
        }
    }

}

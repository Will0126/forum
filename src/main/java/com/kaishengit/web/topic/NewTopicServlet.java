package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.ConfigProp;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

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
                Integer id = new TopicService().saveNewTopic(title,context,nodeid,userid);
                result.put("state","success");
                result.put("data",id);

            } else {
               resp.sendError(400,"参数错误");
            }
         }

        rendJson(resp,result);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //生成编辑框内图片上传的参数
        Auth auth = Auth.create(ConfigProp.get("qiniu.ak"),ConfigProp.get("qiniu.sk"));
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{\"success\":true,\"file_path\":\""+ ConfigProp.get("qiniu,domain")+"$(key)\"}");
        //上传到哪、key是否自动生成、时间
        String token = auth.uploadToken(ConfigProp.get("qiniu.buckname"),null,3600,stringMap);

        List<Node> nodeList = new TopicService().findAllNode();
        req.setAttribute("uploadToken",token);
        req.setAttribute("nodeList",nodeList);
        forward(req,resp,"topic/newtopic");
    }
}

package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelper;
import org.joda.time.DateTime;

import java.util.List;

public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private UserDao userDao = new UserDao();

    public List<Node> findAllNode() {
        return nodeDao.findAllNode();
    }

    public Topic saveNewTopic(String title, String context, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContext(context);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        topic.setCreatetime(DateTime.now().getMillis());

        topicDao.save(topic);
        return  topicDao.findTopic(topic.getUserid(),topic.getCreatetime());

    }

    public Topic findTopicById(String id) {
        Topic topic =  topicDao.findTopicById(id);
        if(topic != null){
            User user = userDao.findByUid(topic.getUserid());
            Node node = nodeDao.finNodeByNodeid(topic.getNodeid());
            topic.setUser(user);
            topic.setNode(node);
            return topic;
        } else {
            return null;
        }
    }
}

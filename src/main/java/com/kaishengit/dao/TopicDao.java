package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.util.DBHelper;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class TopicDao {
    public static void save(Topic topic) {
        String sql = "insert into w_topic(title,context,nodeid,createtime,userid) values(?,?,?,?,?) ";
        DBHelper.update(sql,topic.getTitle(),topic.getContext(), topic.getNodeid(), topic.getCreatetime(),topic.getUserid());
    }

    public Topic findTopicById(String id) {
        String sql = "select * from w_topic where id = ?" ;
        return DBHelper.query(sql,new BeanHandler<Topic>(Topic.class),id);
    }

    public Topic findTopic(Integer userid, Long createtime) {
        String sql = "select * from w_topic where userid = ? and createtime = ?" ;
        return DBHelper.query(sql,new BeanHandler<>(Topic.class),userid,createtime);
    }
}

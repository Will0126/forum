package com.kaishengit.dao;

import com.google.common.collect.Lists;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.DBHelper;
import com.kaishengit.util.EmailUtil;
import com.kaishengit.util.EnCacheUtil;
import com.kaishengit.util.SimpleCache;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TopicDao {
    public static Integer save(Topic topic) {
        String sql = "insert into w_topic(title,context,nodeid,createtime,userid,clicknum,favnum,thanknum,replynum,lastreplytime) values(?,?,?,?,?,?,?,?,?,?) ";
        return DBHelper.insert(sql,topic.getTitle(),topic.getContext(), topic.getNodeid(), topic.getCreatetime(),topic.getUserid(),topic.getClicknum(),topic.getFavnum(),topic.getThanknum(),topic.getReplynum(),topic.getLastreplytime()).intValue();
    }

    public Topic findTopicById(String id) {
        Topic topic = (Topic) EnCacheUtil.get("simpleCache","Topic:" + id);
        if(topic == null) {
            String sql = "select * from w_topic where id = ?" ;
            topic = DBHelper.query(sql,new BeanHandler<Topic>(Topic.class),id);
            EnCacheUtil.set("simpleCache","Topic:" + id,topic);
        }
        return topic;
    }
    public Topic findByIdLoadUserAndNode(Integer id){
        Topic topic = (Topic) EnCacheUtil.get("simpleCache","Topic:" + id);
        //Topic topic = (Topic) SimpleCache.get("Topic:" + id);//缓存key必须唯一 --> Topic:1 Topic:2
        if(topic == null) {
            //缓存内没有存放
            //一次查询，获取topic、username、avatar、nodename
            String sql = "select w_topic.*,w_user.`username`,w_user.`avatar`,w_node.`nodename` from w_topic \n" +
                    "inner join w_user on w_topic.`userid` = w_user.`id` \n"+
                    "inner join w_node on w_topic.`nodeid` = w_node.`id` \n"+
                    "where w_topic.`id` = ?";

            topic = DBHelper.query(sql, new ResultSetHandler<Topic>() {
                @Override
                public Topic handle(ResultSet resultSet) throws SQLException {
                    if(resultSet.next()){
                        //BasicRowProcessor()解析器
                        Topic topic = new BasicRowProcessor().toBean(resultSet,Topic.class);
                        User user = new BasicRowProcessor().toBean(resultSet, User.class);
                        Node node = new BasicRowProcessor().toBean(resultSet,Node.class);

                        topic.setUser(user);
                        topic.setNode(node);
                        return topic;
                    }
                    return null;
                }
            },id);
            //调取数据库信息成功，加入缓存
            //SimpleCache.add("Topic:" + id,topic);
            EnCacheUtil.set("simpleCache","Topic:" + id,topic);
        }
        return topic;

    }

    public void update(Topic topic) {
        //修改数据要删除缓存内的该条数据
        EnCacheUtil.delete("simpleCache","Topic:" + topic.getId());
        String sql = "update w_topic set title = ? context = ?,nodeid = ?,clicknum = ?,favnum = ?,thanknum = ?,replynum = ?,lastreplytime = ? where id = ?";
        DBHelper.update(sql,topic.getTitle(),topic.getContext(), topic.getNodeid(), topic.getClicknum(),topic.getFavnum(),topic.getThanknum(),topic.getReplynum(),topic.getLastreplytime(),topic.getId());
    }

    public int count(Integer nodeid) {
        String sql = "select count(*) from w_topic where nodeid = ?";
        return DBHelper.query(sql, new ScalarHandler<Long>(),nodeid).intValue();
    }
    public int count() {
        String sql = "select count(*) from w_topic";
        return DBHelper.query(sql, new ScalarHandler<Long>()).intValue();
    }


    public List<Topic> findByPage(Integer nodeid, int start, int size) {
        String sql = "select w_topic.* ,w_user.`username`,w_user.`avatar` from w_topic inner join w_user.`id`= w_topic.`userid` where nodeid =? order by lastreplytime desc limit ?,?";
        return DBHelper.query(sql,new TopicUserHandler(),nodeid,start,size);
    }


    public List<Topic> findByPage(int start, int size) {
        String sql = "select * w_topic.* ,w_user.`username`,w_user.`avatar` from w_topic inner join w_user.`id`= w_topic.`userid` order by lastreplytime desc limit ?,?";
        return DBHelper.query(sql,new TopicUserHandler(),start,size);
    }

    private class TopicUserHandler implements ResultSetHandler<List<Topic>> {

        @Override
        public List<Topic> handle(ResultSet resultSet) throws SQLException {
            BasicRowProcessor basicRowProcessor = new BasicRowProcessor();
            List<Topic> topicList = Lists.newArrayList();
            while(resultSet.next()){
                Topic topic = basicRowProcessor.toBean(resultSet,Topic.class);
                User user = basicRowProcessor.toBean(resultSet,User.class);
                topic.setUser(user);
                topicList.add(topic);

            }
            return topicList;

        }
    }
}


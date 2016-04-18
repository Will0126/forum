package com.kaishengit.service;

import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.List;

public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private UserDao userDao = new UserDao();
    private CommentDao commentDao = new CommentDao();
    private FavDao favDao = new FavDao();

    public List<Node> findAllNode() {
        return nodeDao.findAllNode();
    }

    public Integer saveNewTopic(String title, String context, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContext(context);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        topic.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        topic.setClicknum(0);
        topic.setFavnum(0);
        topic.setReplynum(0);
        topic.setThanknum(0);
        topic.setLastreplytime(topic.getCreatetime());

        return topicDao.save(topic);

    }

    public Topic viewTopic(String id) {
        Topic topic =  topicDao.findByIdLoadUserAndNode(Integer.valueOf(id));
//        if(topic != null){
//            User user = userDao.findByUid(topic.getUserid());
//            Node node = nodeDao.finNodeByNodeid(topic.getNodeid());
//            topic.setUser(user);
//            topic.setNode(node);
//            return topic;
//        } else {
//            return null;
//        }
        if(topic == null){
            return null;
        } else {
            topic.setClicknum(topic.getClicknum() + 1);
            topicDao.update(topic);
            return topic;
        }
    }

    public Comment saveNewComment(String commentContext, Topic topic, User user) {
        Comment comment = new Comment();
        //DateTime.now().getMillis()
        comment.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        comment.setUserid(user.getId());
        comment.setTopicid(topic.getId());
        comment.setContext(commentContext);

        comment.setId(commentDao.saveNewComment(comment));
        comment.setTopic(topic);
        comment.setUser(user);

        topic.setReplynum(topic.getReplynum() + 1);
        topic.setLastreplytime(comment.getCreatetime());
        topicDao.update(topic);

        return comment;
    }

    public Page<Topic> showIndeTopic(String node, String pageNo) {


        int pageSize = 20;
        Page<Topic> page;
        if(StringUtils.isNumeric(node)){
            //过滤信息，只查看有关node的
            int count = topicDao.count(Integer.valueOf(node));
            page = new Page<Topic>(pageNo,count,pageSize);
            List<Topic> topicList = topicDao.findByPage(Integer.valueOf(node),page.getStart(),page.getSize());
            page.setItems(topicList);
        }else {
            //All
            int count = topicDao.count();
            page = new Page<Topic>(pageNo,count,pageSize);
            List<Topic> topicList = topicDao.findByPage(page.getStart(),page.getSize());
            page.setItems(topicList);
        }
        return page;
    }

    /**
     * 通过id查找topic
     * @param topicid
     * @return
     */
    public Topic findTopicById(String topicid) {
        return topicDao.findTopicById(topicid);
    }

    /**
     * 查找所有回复
     * @param topicid
     * @return
     */
    public List<Comment> findCommentListByTopicId(Integer topicid) {
        return commentDao.findLoadUserByTopicId(topicid);
    }

    public void favTopic(Topic topic, User user, String action) {
        Fav fav = favDao.findByTopicIdAndUserId(topic.getId(),user.getId());
        if("fav".equals(action)){
            if(fav != null) {
                throw new ServiceException("您已收藏该主题贴");
            } else {
                fav = new Fav();
                fav.setTopicid(topic.getId());
                fav.setUserid(user.getId());
                fav.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                favDao.save(fav);

                topic.setFavnum(topic.getFavnum() + 1);
                topicDao.update(topic);
            }
        } else {
            if(fav != null){
                favDao.delete(fav);
                topic.setFavnum(topic.getFavnum() - 1);
                topicDao.update(topic);
            }
        }
    }

    public Fav findFavTopicIdByAndUserId(Integer topicid, Integer userid) {
        return favDao.findByTopicIdAndUserId(topicid,userid);
    }
}

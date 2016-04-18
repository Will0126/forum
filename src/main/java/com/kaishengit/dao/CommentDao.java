package com.kaishengit.dao;

import com.google.common.collect.Lists;
import com.kaishengit.entity.Comment;
import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelper;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDao {
    public Integer saveNewComment(Comment comment) {
        String sql = "insert into w_comment(context,userid,topicid,createtime) values(?,?,?,?)";
        return DBHelper.insert(sql,comment.getContext(),comment.getUserid(),comment.getTopicid(),comment.getCreatetime()).intValue();
    }


    public List<Comment> findLoadUserByTopicId(Integer topicid) {
        String sql = "select w_comment.*,username,avatar from w_comment inner join w_user on w_comment.userid = w_user.'id' where w_comment.'topicid' = ?";
        return DBHelper.query(sql, new ResultSetHandler<List<Comment>>() {
            @Override
            public List<Comment> handle(ResultSet rs) throws SQLException {
                List<Comment> commentList = Lists.newArrayList();
                //解析器BasicRowProcessor
                BasicRowProcessor rowProcessor = new BasicRowProcessor();
                while (rs.next()){
                    Comment comment = rowProcessor.toBean(rs,Comment.class);
                    User user = rowProcessor.toBean(rs,User.class);
                    comment.setUser(user);
                    commentList.add(comment);
                }
                return commentList;
            }
        }, topicid);
    }
}

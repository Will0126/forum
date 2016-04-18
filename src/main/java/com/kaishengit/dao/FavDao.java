package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.util.DBHelper;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class FavDao {

    public Fav findByTopicIdAndUserId(Integer topicid, Integer userid) {
        String sql = "select * from w_fav where topicid = ? userid = ?";
        return DBHelper.query(sql,new BeanHandler<>(Fav.class),topicid,userid);
    }

    public void save(Fav fav) {
        String sql = "insert into w_fav(topicid,userid,createtime) values(?,?,?)";
        DBHelper.update(sql,fav.getTopicid(),fav.getUserid(),fav.getCreatetime());

    }

    public void delete(Fav fav) {
        String sql = "delete from w_fav where id = ?";
        DBHelper.update(sql,fav.getId());
    }
}

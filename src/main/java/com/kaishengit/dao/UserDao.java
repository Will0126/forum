package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelper;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDao {
    public void save(User user) {
        String sql = "insert into w_user(username,password,email,avatar,createtime) values(?,?,?,?,?,?)";
        DBHelper.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getAvatar(),user.getCreatetime(),user.getState());

    }

    public User findByUsername(String username) {
        String sql = "select * from w_user where username = ?";
        return DBHelper.query(sql,new BeanHandler<>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select * from w_user where email = ?";
        return DBHelper.query(sql,new BeanHandler<>(User.class),email);
    }
}

package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelper;
import com.kaishengit.util.EnCacheUtil;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.mail.Email;

public class UserDao {
    public void save(User user) {
        String sql = "insert into w_user(username,password,email,avatar,createtime,state) values(?,?,?,?,?,?)";
        DBHelper.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getAvatar(),user.getCreatetime(),user.getState());
    }

    public User findByUsername(String username) {
        User user = (User) EnCacheUtil.get("simpleCache","User" + username);
        if(user == null) {
            String sql = "select * from w_user where username = ?";
            user = DBHelper.query(sql,new BeanHandler<>(User.class),username);
            EnCacheUtil.set("simpleCache","User" + username,user);
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = (User) EnCacheUtil.get("simpleCache","User" + email);
        if(user == null){
            String sql = "select * from w_user where email = ?";
            user = DBHelper.query(sql,new BeanHandler<>(User.class),email);
            EnCacheUtil.set("simpleCache","User" + email,user);
        }
        return user;
    }

    public void updata(User user) {
        EnCacheUtil.delete("simpleCache","User" + user.getId());
        String sql = "update w_user set password = ?,email = ?,avatar = ?, loginip = ?, logintime = ?,state = ? where id = ?";
        DBHelper.update(sql,user.getPassword(),user.getEmail(),user.getAvatar(),user.getLoginip(),user.getLogintime(),user.getState(),user.getId());
    }

    public User findByUid(Integer uid) {
        User user = (User) EnCacheUtil.get("simpleCache","User" + uid);
        if(user == null) {
            String sql = "select * from w_user where id = ?";
            user = DBHelper.query(sql,new BeanHandler<>(User.class),uid);
            EnCacheUtil.set("simpleCache","User" + uid,user);
        }
      return user;
    }
}

package com.kaishengit.dao;

import com.kaishengit.entity.ForgetPassword;
import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelper;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class ForgetPasswordDao {
    public void save(ForgetPassword forgetPassword) {
        String sql = "insert into w_forgetpassword(uid,token,createtime) values(?,?,?)";
        DBHelper.update(sql,forgetPassword.getUid(),forgetPassword.getToken(),forgetPassword.getCreatetime());
    }

    public ForgetPassword findByToken(String token) {
        String sql = "select * from w_forgetpassword where token = ?";
        return DBHelper.query(sql,new BeanHandler<ForgetPassword>(ForgetPassword.class),token);

    }

    public void deleteForgetPassword(String token) {
        String sql = "delete from w_forgetpassword where token = ?";
        DBHelper.update(sql,token);

    }
}

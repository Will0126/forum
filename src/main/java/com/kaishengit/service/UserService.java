package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.ConfigProp;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

public class UserService {

    private UserDao userDao = new UserDao();

    /**
     *新增用户
     * @param username
     * @param password
     * @param email
     */
    public void saveNewUser(String username, String password, String email) {

        //新增前再次验证
        if(findByUsername(username) != null){
            throw new ServiceException("注册失败，帐号已被占用");
        }
        if(findByEmail(email) != null){
            throw new ServiceException("注册失败，帐号已被占用");
        }


        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));
        user.setEmail(email);
        //时间生成辅助类库
        user.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        user.setAvatar(ConfigProp.get("user.default.avatar"));
        user.setState(user.USER_STATE_NORMAL);

        userDao.save(user);

    }

    /**
     * 通过username查找用户
     * @param username
     */
    public User findByUsername(String username) {

        return userDao.findByUsername(username);
    }

    /**
     * 通过email查找用户
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     *帐号登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password, String ip) {
        User user = findByUsername(username);
        if(user != null && user.getPassword().equals(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")))){
            //登录成功
            //设置登录时间和登录ip属性
            user.setLoginip(ip);
            user.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            userDao.updata(user);
            return  user;
        }
        return null;

    }
}

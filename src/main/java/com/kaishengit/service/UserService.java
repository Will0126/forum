package com.kaishengit.service;

import com.kaishengit.dao.ForgetPasswordDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.ForgetPassword;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.ConfigProp;
import com.kaishengit.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UserService {

    private UserDao userDao = new UserDao();
    private ForgetPasswordDao forgetPasswordDao = new ForgetPasswordDao();
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

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
        logger.info("新注册用户{}",user.getUsername());

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
            if(user.getState().equals(user.USER_STATE_NORMAL)){
                //帐号正常
                user.setLoginip(ip);
                user.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                userDao.updata(user);
                logger.info("用户{}登录",user.getUsername());
                return  user;
            } else if(user.getState().equals(user.USER_STATE_DISABLE)){
                //帐号禁用
                logger.info("用户{}被禁用",user.getUsername());
                throw new ServiceException("该帐号已禁用");
            } else {
                //帐号未激活
                logger.info("用户{}未激活",user.getUsername());
                throw new ServiceException("该帐号未激活");
            }

        } else {
            throw new ServiceException("账号或密码错误");
        }
    }


    /**
     * 找回密码功能-----通过帐号，向帐号对应的邮箱发送修改密码邮件
     * @param username
     */
    public void forgetPassword(String username) {
        User user = findByUsername(username);
        if(user != null){
            String token = UUID.randomUUID().toString();
            String email = user.getEmail();
            String url = "http://localhost/forget/callback.do?token="+token;
            //发送邮件
            String title = "凯盛论坛-找回密码";
            String msg = user.getUsername() + ":<br>\n" +
                    "点击该链接由凯盛论坛系统发出，点击< href='"+url+"'>链接</a>进行设置新密码，该链接30分钟内有效。<br>\n" +
                    url + "请勿回复";


            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.setToken(token);
            forgetPassword.setUid(user.getId());
            forgetPassword.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

            forgetPasswordDao.save(forgetPassword);
            logger.info("用户{}找回密码",user.getUsername());
            EmailUtil.sendHtmlEmail(title,msg,email);
        }

    }


    /**
     * 验证token是否有效
     * @param token
     */
    public Integer validateForgetPasswordToken(String token) {
        //验证token
        ForgetPassword forgetPassword = forgetPasswordDao.findByToken(token);
        if(forgetPassword == null){
            throw new ServiceException("该链接无效");
        } else {
            //判断是否在30分钟 创建时间+30分钟 > now 有效
            String creatime = forgetPassword.getCreatetime();
            //将String 转化为 DateTime类
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime dateTime = formatter.parseDateTime(creatime);
            //增加30
            dateTime = dateTime.plusMinutes(30);

            if(dateTime.isAfterNow()){
                //在当前时间之后,有效
                return forgetPassword.getUid();
            } else {
                //尝试做删除
                forgetPasswordDao.deleteForgetPassword(token);
                throw new ServiceException("该链接已失效");
            }
        }

    }

    public void forgetPasswordSetNewPassword(String password, String token) {
        ForgetPassword forgetPassword = forgetPasswordDao.findByToken(token);
        if(forgetPassword == null) {
            throw new ServiceException("token无效");
        } else {
            Integer uid = forgetPassword.getUid();
            User user = userDao.findByUid(uid);
            //修改用户密码
            user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));
             userDao.updata(user);

            //修改完成，删除找回密码记录
            //这里使用token删除，没使用uid删除
            forgetPasswordDao.deleteForgetPassword(token);
        }
    }

    /**
     * 设置功能----修改用户信息
     * @param user
     */
    public void updateUser(User user) {
        if(user != null){
            userDao.updata(user);
            logger.info("用户{}修改信息",user.getUsername());
        }
    }
}

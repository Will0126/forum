package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.ConfigProp;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户的邮箱和密码修改
 */
@WebServlet("/user/editUser.do")
public class EditUserServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(isAjaxRequest(request)) {
            //修改的流程的post只能有Ajax调用
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String avatar = request.getParameter("key");

            //这里将session内的值赋值给User，因为是引用数据类型，所以当user的值修改时，session空间内的值会自动修改
            User user = getLoginUser(request);
            Map<String,Object> result = Maps.newHashMap();

            if(StringUtils.isNotEmpty(email)){
                user.setEmail(email);
            }

            if(StringUtils.isNotEmpty(password)){
                user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));
            }

            if(StringUtils.isNotEmpty(avatar)){
                //方便修改照片路径前缀
                avatar = ConfigProp.get("qiniu.domain") + avatar;
                user.setAvatar(avatar);
                //将头像作为参数，传递回去
                result.put("data",avatar);
            }
            new UserService().updateUser(user);
            result.put("state","success");
            rendJson(response,result);
        }
    }

}

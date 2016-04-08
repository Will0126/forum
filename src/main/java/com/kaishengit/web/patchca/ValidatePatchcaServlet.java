package com.kaishengit.web.patchca;

import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validate/patchca.do")
public class ValidatePatchcaServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");

        String result;
        String sessionValue = (String) request.getSession().getAttribute("captcha");
        if(sessionValue.equals(code)){
            result = "true";
        } else {
            result = "false";
        }

        rendText(response,result);

    }
}

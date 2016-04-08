package com.kaishengit.web.patchca;

import com.kaishengit.web.BaseServlet;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/patchca.png")
public class PatchcaServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ConfigurableCaptchaService service = new ConfigurableCaptchaService();
        //设置颜色
        service.setColorFactory(new SingleColorFactory(new Color(26,69,226)));
        //
        service.setFilterFactory(new CurvesRippleFilterFactory(service.getColorFactory()));


        //基础参数设置
        RandomWordFactory wordFactory = new RandomWordFactory();
        wordFactory.setMinLength(6);
        wordFactory.setMaxLength(6);
        service.setWordFactory(wordFactory);

        //设置类型
        resp.setContentType("image/png");

        OutputStream outputStream = resp.getOutputStream();

        //生成验证码
        String captcha = EncoderHelper.getChallangeAndWriteImage(service,"png",outputStream);

        HttpSession session = req.getSession();
        session.setAttribute("captcha",captcha);

        outputStream.flush();
        outputStream.close();

    }
}

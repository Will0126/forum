package com.kaishengit.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 邮件发送工具类
 */
public class EmailUtil {

    public static void sendHtmlEmail(String subject,String context, String toAddress) {

        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(ConfigProp.get("mail.smtp"));//设置邮件服务器地址
        htmlEmail.setAuthentication(ConfigProp.get("mail.username"),ConfigProp.get("mail.password"));
        htmlEmail.setCharset(ConfigProp.get("mail.charset"));
        htmlEmail.setStartTLSEnabled(true);

        try {
            htmlEmail.setFrom(ConfigProp.get("mail.from"));
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(context);

            htmlEmail.addTo(toAddress);

            htmlEmail.send();
        } catch (EmailException e) {
            e.printStackTrace();
            throw new RuntimeException("给" + toAddress + "发送邮件异常",e);
        }


    }




}

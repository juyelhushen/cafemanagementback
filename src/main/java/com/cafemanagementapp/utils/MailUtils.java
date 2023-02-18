package com.cafemanagementapp.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailUtils {

    @Autowired
    private JavaMailSender sender;

    public void messageSender(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hushenjuyel@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(!list.isEmpty())
            message.setCc(getCCArray(list));
        sender.send(message);

    }

    private String[] getCCArray(List<String> ccList) {
        String[] cc = new String[ccList.size()];
        for(int i = 0; i < ccList.size(); i++) {
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void forgetMail(String to, String subject,String password) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("hushenjuyel@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String msgTemplate = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(msgTemplate,"text/html");
        sender.send(message);
    }
}

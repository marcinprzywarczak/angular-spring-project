package com.backend.backend.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jdk.jfr.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service(value = "MailService")
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void sendMail(String name, String login, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@gmail.com", "noreply@spring.com");
            helper.setTo(login);
            helper.setSubject("Hello in my App");

            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("login", login);
            map.put("password", password);

            helper.setText(getMail(map), true);

            javaMailSender.send(helper.getMimeMessage());
        }


        catch (MessagingException ex) {
            System.out.println("blad");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMail(Map<String, String> values) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration().getTemplate("hello-mail.ftlh"),values));
        } catch (Exception e) {

        }
        return content.toString();
    }

}

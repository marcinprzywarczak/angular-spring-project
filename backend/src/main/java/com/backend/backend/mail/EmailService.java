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

    public void sendMail() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@gmail.com", "noreply@spring.com");
            helper.setTo(new String[]{"marcino8928@wp.pl","marcinprzywarczak@gmail.com"});
            helper.setSubject("TEST");
            helper.setText("Test test test");
            helper.setText(getMail(), true);

            javaMailSender.send(helper.getMimeMessage());
        }


        catch (MessagingException ex) {
            System.out.println("blad");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMail() {
        StringBuffer content = new StringBuffer();
        Map<String, String> map = new HashMap<>();
        map.put("firstName", "Jan");
        map.put("lastName", "Nowak");
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration().getTemplate("email-template.ftlh"),map));
        } catch (Exception e) {

        }
        return content.toString();
    }

}

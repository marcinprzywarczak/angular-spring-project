package com.backend.backend.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service(value = "MailService")
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;


    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail() throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@gmail.com", "noreply@spring.com");
            helper.setTo(new String[]{"marcino8928@wp.pl","marcinprzywarczak@gmail.com"});
            helper.setSubject("TEST");
            helper.setText("Test test test");
            helper.setText("http://localhost:4200/lists");

            javaMailSender.send(message);
        }


        catch (MessagingException ex) {
            System.out.println("blad");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}

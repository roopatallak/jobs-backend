package com.genspark.jwtsecurity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender sender;

    @Value("${email.subject}")
    private String subject;

    @Value("${email.to}")
    private String emailTo;

    @Value("${email.body}")
    private String body;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        //from and to same email ID
        message.setFrom(emailTo);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(body);

        log.info("Sending email subject = " + subject);
        sender.send(message);
        log.info("Email sent");

    }
}

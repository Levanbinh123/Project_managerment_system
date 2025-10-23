package com.example.project_management_system.service.impl;

import com.example.project_management_system.service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
        String subject="join project team Invitation";
        String text="Click the link below to join project team Invitation : "+" "+link;
        mimeMessageHelper.setText(text,true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(userEmail);
        try {
            mailSender.send(mimeMessage);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}

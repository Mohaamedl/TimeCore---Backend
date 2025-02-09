package com.odin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Service for handling email operations.
 * Provides email sending functionality for verification codes and notifications.
 */
@Service
public class EmailService {
    
    private final JavaMailSender javaMailSender;

    /**
     * Constructs EmailService with mail sender
     * @param javaMailSender The JavaMailSender implementation to use
     */
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends a verification code via email
     * @param email Recipient email address
     * @param otp One-time password to send
     * @throws MessagingException if email sending fails
     */
    public void sendVerificationOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(email);
        helper.setSubject("Your TimeCore Verification Code");
        helper.setText("Your verification code is: " + otp);
        
        javaMailSender.send(message);
    }
}

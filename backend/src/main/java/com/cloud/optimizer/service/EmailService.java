package com.cloud.optimizer.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generate6DigitOtp() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendEmailOtp(String toEmail, String otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply.costmatrix@gmail.com");
            message.setTo(toEmail);
            message.setSubject("CostMatrix Registration Email Verification OTP");
            message.setText("Welcome to CostMatrix FinOps Platform!\n\n" +
                    "Your 6-digit Email Verification OTP is: " + otpCode + "\n\n" +
                    "This code will expire in 10 minutes. Please enter it on the registration verification screen to complete your account setup.\n\n" +
                    "Regards,\nCostMatrix Team");
            mailSender.send(message);
        } catch (Exception e) {
            // Log fallback for local dev when SMTP credentials are default
            System.out.println("=================================================");
            System.out.println("📧 [CostMatrix Email OTP Dispatch]");
            System.out.println("Target Email: " + toEmail);
            System.out.println("Generated OTP: " + otpCode);
            System.out.println("=================================================");
        }
    }
}

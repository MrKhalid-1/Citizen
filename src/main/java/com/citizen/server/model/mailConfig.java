package com.citizen.server.model;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class mailConfig {

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String smtpStarttls;

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private String smtpPort;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(Integer.parseInt(smtpPort));
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", smtpStarttls);

        return mailSender;
    }
}

/*
 * private void sendCredentials(String email, String username, String password) { String subject =
 * "Your Account Credentials"; String messageText = "Your username: " + username + "\nYour password: " + password +
 * "\n\n\n" + "\nNote: If your company is registered as a wholesaler and you need to add products, users, base units," +
 * " or product categories, please contact the super admin at abkhalid291@gmail.com.\n\n" + "Best regards,\nYour Team";
 * try { SimpleMailMessage message = new SimpleMailMessage(); message.setFrom(emailUsername); message.setTo(email);
 * message.setSubject(subject); message.setText(messageText); mailSender.send(message); } catch (Exception e) { throw
 * new RuntimeException(e); } }
 */
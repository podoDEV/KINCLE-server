package com.podo.climb.config;

import com.podo.climb.secure.SecureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class MailConfig {

    private final SecureData secureData;

    public MailConfig(SecureData secureData) {
        this.secureData = secureData;
    }

    @Primary
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(secureData.getMailHost());
        mailSender.setPort(secureData.getMailPort());
        mailSender.setUsername(secureData.getMailUser());
        mailSender.setPassword(secureData.getMailPassword());
        mailSender.setJavaMailProperties(getMailProperties());
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        return properties;
    }
}
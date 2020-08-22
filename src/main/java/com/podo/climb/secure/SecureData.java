package com.podo.climb.secure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Getter
public class SecureData {

    private String jdbcDriverClassName;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    private String filePath;

    private String mailHost;
    private Integer mailPort;
    private String mailUser;
    private String mailPassword;


}

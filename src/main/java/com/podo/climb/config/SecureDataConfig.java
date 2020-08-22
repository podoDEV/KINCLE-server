package com.podo.climb.config;

import com.podo.climb.secure.SecureData;
import com.podo.climb.secure.SecureDataFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureDataConfig {

    @Bean
    public SecureData secureData() {
        SecureDataFactoryBean secureDataFactoryBean = new SecureDataFactoryBean();
        return secureDataFactoryBean.getObject();
    }
}

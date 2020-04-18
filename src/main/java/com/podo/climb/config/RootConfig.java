package com.podo.climb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = "com.podo.climb", excludeFilters = @ComponentScan.Filter(Controller.class))
@Import(com.podo.climb.config.SecurityConfig.class)
public class RootConfig {

}

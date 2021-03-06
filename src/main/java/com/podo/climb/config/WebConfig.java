package com.podo.climb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podo.climb.entity.converter.BoardFilterTypeConverter;
import com.podo.climb.entity.converter.MembersBoardFlagTypeConverter;
import com.podo.climb.secure.SecureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.podo.climb")
public class WebConfig implements WebMvcConfigurer {

    private final SecureData secureData;

    public WebConfig(SecureData secureData) {
        this.secureData = secureData;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login/form").setViewName("loginForm");
        registry.addViewController("/upload/form").setViewName("uploadForm");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BoardFilterTypeConverter());
        registry.addConverter(new MembersBoardFlagTypeConverter());
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/resources/");

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + secureData.getFilePath());

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

}

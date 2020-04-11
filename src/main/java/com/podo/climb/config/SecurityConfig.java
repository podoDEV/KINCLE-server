package com.podo.climb.config;

import com.podo.climb.controller.advice.UserDeniedHandler;
import com.podo.climb.encoder.Sha256PasswordEncoder;
import com.podo.climb.model.MemberRoleType;
import com.podo.climb.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false)
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/signin").permitAll()
//                .antMatchers("/upload").hasAuthority(MemberRoleType.MEMBER.toString())
//                .antMatchers("/v1/board").hasAuthority(MemberRoleType.MEMBER.toString())
//                .antMatchers("/v1/gym").hasAuthority(MemberRoleType.MEMBER.toString())
//                .antMatchers("/v1/member/favorite").hasAuthority(MemberRoleType.MEMBER.toString())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(userDeniedHandler())
                .and()
                .logout();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                    authenticationProvider(authenticationProvider());
    }

    @Bean
    public UserDeniedHandler userDeniedHandler() {
        return new UserDeniedHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(new Sha256PasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }


}

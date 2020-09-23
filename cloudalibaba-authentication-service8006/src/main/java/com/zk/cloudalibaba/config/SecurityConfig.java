package com.zk.cloudalibaba.config;

import com.sun.javaws.security.JavaWebStartSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    //重写方法
    protected void configure(HttpSecurity security) throws Exception {
        security.httpBasic()
                .and()
                .authorizeRequests().anyRequest().authenticated();//所有的资源必须在登录之后才可以访问
    }
}

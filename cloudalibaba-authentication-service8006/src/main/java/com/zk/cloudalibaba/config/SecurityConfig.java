package com.zk.cloudalibaba.config;

import com.sun.javaws.security.JavaWebStartSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    //重写方法
    protected void configure(HttpSecurity security) throws Exception {
        security.csrf().disable()//接触跨站攻击防御
                .formLogin()
                .loginPage("/login.html")//登录页面
                .loginProcessingUrl("/login")//登录url
                .defaultSuccessUrl("/index.html")//登录以后跳转页面
                .and()
                .authorizeRequests()
                .antMatchers("/login.html","/login").permitAll()//这两个资源对所有请求开放
                .antMatchers("/biz1", "/biz2")
                .hasAnyAuthority("ROLE_user","ROLE_admin")//以上两个资源必须有user和admin权限可以访问
                .antMatchers("/syslog", "/sysuser")
                .hasAnyAuthority("sys:log")
                .anyRequest().authenticated();
    }

    //配置用户权限角色信息
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("12345"))
                .roles("user")
                    .and()
                .withUser("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("admin")
                    .and()
                .passwordEncoder(passwordEncoder());
    }

    //对用户密码进行加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //静态资源不做权限控制
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/*","/image/*");//静态资源不做权限控制
    }
}

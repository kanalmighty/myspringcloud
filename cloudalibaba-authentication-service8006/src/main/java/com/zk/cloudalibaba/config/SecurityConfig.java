package com.zk.cloudalibaba.config;

import com.sun.javaws.security.JavaWebStartSecurity;
import com.zk.cloudalibaba.auth.ExpireSessionStrategy;
import com.zk.cloudalibaba.auth.FailureAuthenticationHandler;
import com.zk.cloudalibaba.auth.SuccessAuthenticationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SuccessAuthenticationHandler successAuthenticationHandler;

    @Resource
    private ExpireSessionStrategy expireSessionStrategy;
    @Resource
    private com.zk.cloudalibaba.auth.FailureAuthenticationHandler failureAuthenticationHandler;
    @Override
    //重写方法
    protected void configure(HttpSecurity security) throws Exception {
        security.csrf().disable()//接触跨站攻击防御
                .formLogin()
                .loginPage("/login.html")//登录页面
                .loginProcessingUrl("/login")//登录url
//                .defaultSuccessUrl("/index.html")//登录以后跳转页面
                .successHandler(successAuthenticationHandler)
//                .failureUrl("/login.html")
                .failureHandler(failureAuthenticationHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login.html","/login").permitAll()//这两个资源对所有请求开放
                .antMatchers("/biz1", "/biz2")
                .hasAnyAuthority("ROLE_user","ROLE_admin")//以上两个资源必须有user和admin权限可以访问
                .antMatchers("/syslog", "/sysuser")
                .hasAnyAuthority("sys:log")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login.html")//session超时跳转的url
                .sessionFixation().migrateSession()//重新登录以后，创新新的session,复制旧session的属性到新session,旧的将无效
                .maximumSessions(1)//最大session数量为1
                .maxSessionsPreventsLogin(false)//true表示登录之后不允许再次登录，false允许再次登录，但是之前的登录会被强制下线
                .expiredSessionStrategy(expireSessionStrategy);
                ;
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

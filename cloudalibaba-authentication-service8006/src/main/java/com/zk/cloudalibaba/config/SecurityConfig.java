package com.zk.cloudalibaba.config;

import com.sun.javaws.security.JavaWebStartSecurity;
import com.zk.cloudalibaba.auth.AuthUserDetails;
import com.zk.cloudalibaba.auth.ExpireSessionStrategy;
import com.zk.cloudalibaba.auth.FailureAuthenticationHandler;
import com.zk.cloudalibaba.auth.SuccessAuthenticationHandler;
import com.zk.cloudalibaba.auth.jwt.AuthJwtTokenFilter;
import com.zk.cloudalibaba.service.AuthRuleService;
import com.zk.cloudalibaba.service.AuthUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SuccessAuthenticationHandler successAuthenticationHandler;

    @Resource
    private ExpireSessionStrategy expireSessionStrategy;
    @Resource
    AuthRuleService authRuleService;
    @Resource
    private com.zk.cloudalibaba.auth.FailureAuthenticationHandler failureAuthenticationHandler;

    @Resource
    private AuthUserDetailsService userDetailsService;

    @Resource
    private AuthJwtTokenFilter authJwtTokenFilter;
    @Override
    //重写方法
    protected void configure(HttpSecurity security) throws Exception {
        security.csrf().disable()//接触跨站攻击防御
//                .formLogin()
//                .loginPage("/login.html")//登录页面
//                .loginProcessingUrl("/login")//登录url
//                .defaultSuccessUrl("/index.html")//登录以后跳转页面
//                .successHandler(successAuthenticationHandler)
//                .failureUrl("/login.html")
//                .failureHandler(failureAuthenticationHandler)
//                .and()
                //一定要放到UsernamePasswordAuthenticationFilter前执行，表示走了前面的filter后面的filter就不用执行了
                .addFilterBefore(authJwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login.html","/login","/authentication", "refresh").permitAll()//这两个资源对所有请求开放
                .antMatchers("/index").authenticated()//index只要认证了都可以访问
                .anyRequest().access("@authRuleService.hasPermission(request,authentication)")//其他请求都要用hasPermission方法判断一下
//                .antMatchers("/biz1", "/biz2")
//                .hasAnyAuthority("ROLE_user","ROLE_admin")//以上两个资源必须有user和admin权限可以访问
//                .antMatchers("/syslog")//资源路径
//                .hasAuthority("/syslog")//资源标识
//                .antMatchers("/sysuser")
//                .hasAuthority("/sysuser")
//                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                //改为无状态的session,就是开发过程冲不需要使用session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
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
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("12345"))
//                .roles("user")
//                    .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("12345"))
//                .roles("admin")
//                    .and()
//                .passwordEncoder(passwordEncoder());

        //从数据库中动态加载用户信息
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

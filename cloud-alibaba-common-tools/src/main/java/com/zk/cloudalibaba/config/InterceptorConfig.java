package com.zk.cloudalibaba.config;

import com.zk.cloudalibaba.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//注册拦截器
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        //注册自己的拦截器并设置拦截的请求路径
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
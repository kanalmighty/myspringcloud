package com.zk.cloudalibaba;

import com.zk.cloudalibaba.tools.HttpUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zk.cloudalibaba.mapper")
public class CloudAlibabaPassportMain8005{
    private ApplicationContext applicationContext;
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaPassportMain8005.class, args);

    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    @Bean
//    public HttpUtils httpUtils(){
//
//        return new HttpUtils();
//    }

}

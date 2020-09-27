package com.zk.cloudalibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.zk.cloudalibaba.mapper")
public class cloudalibabaauthmain8006 {
    public static void main(String[] args) {
        SpringApplication.run(cloudalibabaauthmain8006.class,args);
    }
}

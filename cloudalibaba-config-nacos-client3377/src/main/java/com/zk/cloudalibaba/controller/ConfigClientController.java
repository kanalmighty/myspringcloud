package com.zk.cloudalibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //支持nacos动态刷新
@RequestMapping(value = "/config")
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @RequestMapping(value = "/configinfo")
    public String getConfigInfo(){
        return configInfo;
    }
}

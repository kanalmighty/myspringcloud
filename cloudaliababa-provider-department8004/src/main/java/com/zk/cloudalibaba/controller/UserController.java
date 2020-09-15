package com.zk.cloudalibaba.controller;



import com.zk.cloudalibaba.entities.User;
import com.zk.cloudalibaba.service.UserService;
import com.zk.springcloud.entities.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(description = "用户管理")
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    String serverPort;
    @ApiOperation(value = "更新用户", notes = "更新用户",httpMethod = "PUT")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "user", value = "user", required = true, dataType = "com.zk.springcloud.entities.User",paramType="body")
//    })
    @RequestMapping(value = "/",method = RequestMethod.PUT)
    public CommonResult updateUser(User user){
        int result = userService.updateUserById(user);
        log.info("更新成功,id为" + result+serverPort);
        if (result > 0){
            return new CommonResult(200,"成功"+serverPort,result);
        } else {
            return new CommonResult(-1,"失败",null);
        }
    }



}

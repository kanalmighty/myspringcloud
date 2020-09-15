package com.zk.cloudalibaba.controller;

import com.zk.cloudalibaba.entities.Account;
import com.zk.cloudalibaba.entities.Department;
import com.zk.cloudalibaba.entities.User;
import com.zk.cloudalibaba.service.UserService;
import com.zk.springcloud.entities.CommonResult;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/order/")
@Slf4j
@Api(description = "用户管理")
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private UserService userService;

    @Value("${service-url.nacos-department-service}")
    private String departmentServiceUrl;
    @Value("${service-url.nacos-account-service}")
    private String accountServiceUrl;

    @RequestMapping(value = "/consumer/{id}", method = RequestMethod.GET)
    public String getPaymentInfo(@PathVariable("id") Integer id) {
        return  restTemplate.getForObject(departmentServiceUrl + "/payment/" + id, String.class);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户", notes = "更新用户",httpMethod = "PUT")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "user", value = "user", required = true, dataType = "com.zk.cloudalibaba.entities.User",paramType="body")
//    })
    @GlobalTransactional(name = "update",rollbackFor = Exception.class)
    public CommonResult updateOrderByPK(@RequestBody User user) {
        int num = userService.updateUserById(user);
        Department department = new Department();
        Account account = new Account();
        department.setId(1);
        department.setName("ai");
        account.setAccountNumber("8848");
        account.setId(1);
        account.setUseId(1);
        System.out.println(account.toString());
        restTemplate.put(departmentServiceUrl + "/department/detail", department);
        restTemplate.put(accountServiceUrl + "/account/detail", account);
        return new CommonResult(200,"ok",null);

    }
}

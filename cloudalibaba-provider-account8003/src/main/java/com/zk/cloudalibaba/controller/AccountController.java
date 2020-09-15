package com.zk.cloudalibaba.controller;

import com.zk.cloudalibaba.entities.Account;
import com.zk.cloudalibaba.service.AccountSerivce;
import com.zk.springcloud.entities.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AccountSerivce accountSerivce;



    @RequestMapping(value = "/detail", method = RequestMethod.PUT)
    public CommonResult updateAccountByPK(@RequestBody Account account) {
        System.out.println(account.toString());
        CommonResult commonResult =  null;
        int i = accountSerivce.updateAccountByPrimaryKey(account);
        if (i==1){
            commonResult =  new CommonResult(200,"ok", null);
        } else {
        }
        return commonResult;

    }
}

package com.zk.cloudalibaba.controller;

import com.zk.cloudalibaba.annotation.PassToken;
import com.zk.cloudalibaba.annotation.UserLoginToken;
import com.zk.cloudalibaba.entities.CommonResult;
import com.zk.cloudalibaba.entities.User;
import com.zk.cloudalibaba.excepition.ServiceException;
import com.zk.cloudalibaba.service.LoginService;
import com.zk.cloudalibaba.tools.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/Login")
@Api(description = "用户登录")
public class LoginController {

    @Autowired
    LoginService loginService;
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    @PassToken
    @ApiOperation(value = "用户登录", notes = "用户登录",httpMethod = "POST")
    public CommonResult userlogin(@RequestBody User user){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        CommonResult commonResult = doLogin(response, user);
        return commonResult;
    }

    public CommonResult doLogin(HttpServletResponse response,User user){
        CommonResult commonResult =  null;
        try {
            String token = loginService.login(response, user);
            commonResult = new CommonResult(200, "成功"+token, null);
        } catch (ServiceException e) {
            commonResult = new CommonResult(400, e.getMessage(), null);
        }
        return commonResult;
    }

    @UserLoginToken
    @RequestMapping(value = "/usercenter", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录后访问", notes = "用户登录后访问",httpMethod = "POST")
    public User getMessageWithoutToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Cookie[] cookies = attributes.getRequest().getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                token = cookie.getValue();
            }
        }


        // 取出token中带的用户id 进行操作

        return loginService.getUserByToken(token);
    }
}

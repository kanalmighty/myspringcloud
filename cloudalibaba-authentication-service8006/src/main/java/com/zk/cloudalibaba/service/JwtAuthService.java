package com.zk.cloudalibaba.service;

import com.zk.cloudalibaba.auth.jwt.JwtTokenUtil;
import com.zk.springcloud.entities.CommonResult;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Component
public class JwtAuthService {

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    AuthenticationManager authenticationManager;
    //登录认证换取jwt令牌
    public String login(String username, String password) {
        //通过username和password获取token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            //对token进行认证，成功则返回authentication对象

            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) { //可以在这里自定义异常;
            System.out.println(e.getMessage());
        }
        //获取用户的信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //创建令牌
        return jwtTokenUtil.generateToken(userDetails);
    }


    public String refreshToken(String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }
}

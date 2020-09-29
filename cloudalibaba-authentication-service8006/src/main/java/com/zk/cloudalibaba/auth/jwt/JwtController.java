package com.zk.cloudalibaba.auth.jwt;

import com.zk.cloudalibaba.auth.jwt.JwtTokenUtil;
import com.zk.cloudalibaba.service.JwtAuthService;
import com.zk.springcloud.entities.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class JwtController {
    @Resource
    JwtAuthService jwtAuthService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authentication/{username}/{password}",method = RequestMethod.GET)
    public CommonResult login(@PathVariable String username,@PathVariable String password){


        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return new CommonResult(400,"error",null);
        }
        try {
            return new CommonResult(200,"success",jwtAuthService.login(username, password));
        }catch (Exception e){
            return  new CommonResult(401,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/refreshtoken")
    public  CommonResult refresh(@RequestHeader("${jwt.header}") String oldToken){
        String token = jwtAuthService.refreshToken(oldToken);
        return new CommonResult(200,"success",token);
    }

    @RequestMapping(value = "/hello")
    public CommonResult hello(){
        return new CommonResult(200,"success",null);
    }
}
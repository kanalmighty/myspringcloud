package com.zk.cloudalibaba.service;

import com.zk.cloudalibaba.mapper.AuthUserDetailMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AuthRuleService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private AuthUserDetailMapper mapper;
    
    //判断某用户是否具有该request资源的访问权限
    
    public boolean hasPermisstion(HttpServletRequest request, Authentication authentication){
        Object principal = authentication.getPrincipal();
        
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            //获取用户可访问的uri
            List<String> auth = mapper.getAuthByUserName(username);
            String requestURI = request.getRequestURI();

            //判断获取用户可访问的uri中是否匹配用户请求的url
            return auth.stream().anyMatch(
                    url->antPathMatcher.match(url,request.getRequestURI())
            );
        }
        return false;

    }
    
}

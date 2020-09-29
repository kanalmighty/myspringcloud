package com.zk.cloudalibaba.auth.jwt;

import com.zk.cloudalibaba.service.AuthUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.One;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthJwtTokenFilter extends OncePerRequestFilter {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    AuthUserDetailsService authUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = httpServletRequest.getHeader(jwtTokenUtil.getHeader());
        if(!StringUtils.isEmpty(jwtToken)){
            //从token中获取用户名,getUsernameFromToken从会用token体中的securet对token进行解签，然后获取username
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            //用户名不为空且该用户没有进行过认证
            if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                //获取用户信息
                UserDetails userDetails = authUserDetailsService.loadUserByUsername(username);
                //校验token是否过期,如果没有过期
                if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
                    //给使用该token的用户进行授权
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

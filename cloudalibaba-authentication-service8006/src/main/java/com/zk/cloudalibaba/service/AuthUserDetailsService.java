package com.zk.cloudalibaba.service;

import com.zk.cloudalibaba.auth.AuthUserDetails;
import com.zk.cloudalibaba.mapper.AuthUserDetailMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthUserDetailsService implements UserDetailsService {
    @Resource
    private AuthUserDetailMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //加载基础用户信息
        AuthUserDetails authUserDetails =  mapper.getUserDetailsByName(name);
        //获取用户角色id
        List<String> roleIds = mapper.getRoleByUserName(name);

        //通过用户角色列表加载用户资源权限列表
        List<String> auths = mapper.getAuthByRoleId(roleIds);

        //角色是一种特殊的权限，ROLE_前缀
        roleIds = roleIds.stream().map(rc->"ROLE_" + rc).collect(Collectors.toList());

        //把ROLE_角色加入权限列表
        auths.addAll(roleIds);

        authUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",auths)));

        return authUserDetails;
    }


}

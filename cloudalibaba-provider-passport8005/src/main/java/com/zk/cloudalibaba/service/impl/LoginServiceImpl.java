package com.zk.cloudalibaba.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zk.cloudalibaba.entities.User;
import com.zk.cloudalibaba.excepition.ServiceException;
import com.zk.cloudalibaba.mapper.UserMapper;

import com.zk.cloudalibaba.service.LoginService;
import com.zk.cloudalibaba.tools.RedisUtil;
import com.zk.cloudalibaba.tools.SystemUserError;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    public static final String COOKIE_NAME_TOKEN = "token";
    /**
     * token过期时间，2天
     */
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
    @Override
    public String login(HttpServletResponse response, User user) throws ServiceException {
        User userResult = userMapper.selectByname(user.getName());
        if (userResult==null) {
            throw new ServiceException("no such user");
        }
        if (!userResult.getPassword().equals(user.getPassword())){
            SystemUserError loginFailed = SystemUserError.LOGIN_FAILED;
            throw new ServiceException(loginFailed.getMsg());
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        addCookie(response,token,user);
        System.out.println(COOKIE_NAME_TOKEN + "::" + token);
        return COOKIE_NAME_TOKEN + "::" + token;
    }

    private void addCookie(HttpServletResponse response,String token,User user){
        //将token存入到redis
        redisUtil.setKey(COOKIE_NAME_TOKEN + "::" + token, JSON.toJSONString(user));
        //将token写入cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public User getUserByToken(String token)  {
                            return JSONObject.parseObject(redisUtil.getValue(COOKIE_NAME_TOKEN + "::" + token),User.class);
    }
}

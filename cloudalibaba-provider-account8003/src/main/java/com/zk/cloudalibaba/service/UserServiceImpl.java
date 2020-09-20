package com.zk.cloudalibaba.service;


import com.zk.cloudalibaba.entities.User;
import com.zk.cloudalibaba.mapper.UserMapper;
import com.zk.cloudalibaba.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {



    @Resource
    private UserMapper userMapper;


    public int updateUserById(User user){
        return userMapper.updateByPrimaryKey(user);
    }

}

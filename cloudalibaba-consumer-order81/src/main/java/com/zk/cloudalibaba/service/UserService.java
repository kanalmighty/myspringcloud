package com.zk.cloudalibaba.service;

import com.zk.springcloud.entities.User;
import org.apache.ibatis.annotations.Param;


public interface UserService {
    public int updateUserById(com.zk.cloudalibaba.entities.User user);
}

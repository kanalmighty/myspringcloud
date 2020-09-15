package com.zk.cloudalibaba.dao;

import com.zk.springcloud.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    int createUser(User user);

    @Select("select * from user_info where id = #{id}")
    User getUser(@Param("id") Long id);
}

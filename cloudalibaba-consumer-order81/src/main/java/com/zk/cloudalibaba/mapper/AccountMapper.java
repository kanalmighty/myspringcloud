package com.zk.cloudalibaba.mapper;

import com.zk.cloudalibaba.entities.Account;

public interface AccountMapper {
    int insert(Account record);

    int insertSelective(Account record);
}
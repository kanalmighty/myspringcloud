package com.zk.cloudalibaba.service.impl;

import com.zk.cloudalibaba.entities.Account;
import com.zk.cloudalibaba.mapper.AccountMapper;
import com.zk.cloudalibaba.service.AccountSerivce;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountSerivce {


    @Resource
    private AccountMapper accountMapper;

    @Override
    public int updateAccountByPrimaryKey(Account account) {
        return accountMapper.updateByPrimaryKey(account);
    }
}


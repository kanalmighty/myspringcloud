package com.zk.cloudalibaba.service;

import com.zk.cloudalibaba.entities.User;
import com.zk.cloudalibaba.excepition.ServiceException;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {

        String login(HttpServletResponse response, User user) throws ServiceException;
        User getUserByToken(String token);

}

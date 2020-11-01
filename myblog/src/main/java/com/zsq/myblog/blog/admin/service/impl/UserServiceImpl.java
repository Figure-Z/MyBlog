package com.zsq.myblog.blog.admin.service.impl;

import com.zsq.myblog.blog.admin.dao.UserDao;
import com.zsq.myblog.blog.admin.entity.User;
import com.zsq.myblog.blog.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.checkUser(username,password);
        return user;
    }
}

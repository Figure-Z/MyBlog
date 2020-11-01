package com.zsq.myblog.blog.admin.service;

import com.zsq.myblog.blog.admin.entity.User;

public interface UserService {

    User checkUser(String username,String password);

}

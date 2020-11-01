package com.zsq.myblog.blog.admin.dao;

import com.zsq.myblog.blog.admin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from z_user where username=#{username} and password=#{password}")
    User checkUser(String username,String password);

}

package com.zsq.myblog.blog.admin.service;

import com.zsq.myblog.blog.admin.entity.Type;

import java.util.List;


public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    List<Type> getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);

}

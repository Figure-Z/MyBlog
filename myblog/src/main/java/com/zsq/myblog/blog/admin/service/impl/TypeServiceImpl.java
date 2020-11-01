package com.zsq.myblog.blog.admin.service.impl;

import com.zsq.myblog.blog.admin.dao.TypeDao;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.blog.admin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;


    @Override
    @Transactional
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getType(id);
    }

    @Override
    public List<Type> getAllType() {
        return typeDao.getAllType();
    }

    @Override
    public List<Type> getAllTypeAndBlog() {
        return typeDao.getAllTypeAndBlog();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Override
    @Transactional
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeDao.deleteType(id);
    }
}

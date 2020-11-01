package com.zsq.myblog.blog.admin.entity;

//类型

import java.util.ArrayList;
import java.util.List;

public class Type {

    private long id;
    private String name;

    private List<Blog> blogs = new ArrayList<>();

    private Long blogNum;

    public Type() {
    }

    public Type(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(Long blogNum) {
        this.blogNum = blogNum;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

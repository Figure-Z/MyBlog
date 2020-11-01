package com.zsq.myblog.blog.admin.service;

import com.github.pagehelper.PageInfo;
import com.zsq.myblog.blog.admin.entity.Blog;
import com.zsq.myblog.common.Result;
import com.zsq.myblog.showVo.*;
import com.zsq.myblog.utils.Search;

import java.util.List;

public interface BlogService {

    List<A_blogs> getAllBlog();

    Blog getBlogById(long id);

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);

    //List<Blog> getBlogBySearch(BlogSearch blogSearch);

    List<F_blogs> getByTypeId(Long typeId);

    List<F_blogs> getFirstPageBlogs();

    List<F_blogs> getSearchBlog(String query);

    D_blog getDetailedBlog(Long id);


    Integer getBlogTotal();

    Integer getBlogViewTotal();

}

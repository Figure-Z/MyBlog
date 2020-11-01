package com.zsq.myblog.blog.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.myblog.blog.admin.dao.BlogDao;
import com.zsq.myblog.blog.admin.entity.Blog;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.blog.admin.service.BlogService;
import com.zsq.myblog.common.Result;
import com.zsq.myblog.exception.NotFoundException;
import com.zsq.myblog.showVo.*;
import com.zsq.myblog.utils.MarkdownUtils;
import com.zsq.myblog.utils.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;


    @Override
    public List<A_blogs> getAllBlog() {
        return blogDao.getAllBlog();
    }

    @Override
    public Blog getBlogById(long id) {
        return blogDao.getBlogById(id);
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogDao.saveBlog(blog);
    }

    @Transactional
    @Override
    public int updateBlog(ShowBlog showBlog) {
        return blogDao.updateBlog(showBlog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlog(id);
    }

    @Override
    public List<F_blogs> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public List<F_blogs> getFirstPageBlogs() {
        return blogDao.getFirstPageBlogs();
    }

    @Override
    public List<F_blogs> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    @Override
    public D_blog getDetailedBlog(Long id) {

        D_blog detailedBlog = blogDao.getDetailedBlog(id);
        if (detailedBlog==null){
            throw new NotFoundException("此博客不存在");
        }
        //markdown格式处理
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        //增加浏览数量
        blogDao.updateView(id);
        //获取评论增加
        blogDao.getCommentCountById(id);

        return detailedBlog;
    }

    @Override
    public Integer getBlogTotal() {
        return blogDao.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return blogDao.getBlogViewTotal();
    }

//    @Override
//    public List<Blog> getBlogBySearch(BlogSearch blogSearch) {
//        return blogDao.searchByTitleOrType(blogSearch);
//    }



}

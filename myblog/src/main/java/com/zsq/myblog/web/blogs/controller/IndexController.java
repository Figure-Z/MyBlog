package com.zsq.myblog.web.blogs.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.myblog.blog.admin.dao.BlogDao;
import com.zsq.myblog.blog.admin.entity.Blog;
import com.zsq.myblog.blog.admin.entity.Comment;
import com.zsq.myblog.blog.admin.service.BlogService;
import com.zsq.myblog.blog.admin.service.CommentService;
import com.zsq.myblog.showVo.D_blog;
import com.zsq.myblog.showVo.F_blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/")
    public String indexPage(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, RedirectAttributes attributes){

        PageHelper.startPage(pageNum,4);
        List<F_blogs> allFirstPageBlog = blogService.getFirstPageBlogs();
        PageInfo<F_blogs> pageInfo = new PageInfo<>(allFirstPageBlog);

        model.addAttribute("pageInfo",pageInfo);

        return "index";
    }

    //有问题
    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query){

        PageHelper.startPage(pageNum, 1000);
        List<F_blogs> searchBlog = blogService.getSearchBlog(query);
        PageInfo<F_blogs> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("key", query);

        return "search";
    }

    @RequestMapping("/footer/message")
    public String blogMessage(Model model){

        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);

        return "index :: blogMessage";
    }

    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        //获取博客
        D_blog detailedBlog = blogService.getDetailedBlog(id);
        //获取评论
        List<Comment> comments = commentService.listCommentByBlogId(id);
        //加入评论和博客
        model.addAttribute("comment",comments);
        model.addAttribute("blog",detailedBlog);

        return "blog";
    }

}

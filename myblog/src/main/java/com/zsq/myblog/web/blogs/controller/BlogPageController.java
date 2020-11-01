package com.zsq.myblog.web.blogs.controller;


import com.zsq.myblog.blog.admin.dao.BlogDao;
import com.zsq.myblog.showVo.A_blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BlogPageController {

    @Autowired
    private BlogDao blogDao;

    @RequestMapping("/archives")
    public String archives(Model model){
        List<A_blogs> blogs = blogDao.getAllBlog();
        model.addAttribute("blogs",blogs);
        return "archives";
    }
    @RequestMapping("/friends")
    public String friends(){
        return "friends";
    }

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

}

package com.zsq.myblog.blog.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.myblog.blog.admin.entity.Blog;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.blog.admin.entity.User;
import com.zsq.myblog.blog.admin.service.BlogService;
import com.zsq.myblog.blog.admin.service.TypeService;
import com.zsq.myblog.common.Result;
import com.zsq.myblog.showVo.A_blogs;
import com.zsq.myblog.showVo.BlogSearch;
import com.zsq.myblog.showVo.ShowBlog;
import com.zsq.myblog.utils.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/blogs")
    public String adminBlogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<A_blogs> list = blogService.getAllBlog();
        PageInfo<A_blogs> pageInfo = new PageInfo<A_blogs>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogarctic";
    }

    @RequestMapping("/blog_input")
    public String blog_input(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("blog", new Blog());
        return "admin/blog-input";
    }

    @PostMapping(value = "/save_blog")
    public String saveBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        //登录时将用户放入的session
        blog.setUser((User) session.getAttribute("user"));
        //设置type属性
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //设置用户id
        blog.setUserId(blog.getUser().getId());

        int result = blogService.saveBlog(blog);
        if (result==0){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/blogs";
    }

    @RequestMapping("/blog/{id}/update")
    public String blog_update(@PathVariable long id,Model model) {

        Blog blog = blogService.getBlogById(id);
        List<Type> allType = typeService.getAllType();
        model.addAttribute("blog", blog);
        model.addAttribute("types", allType);

        return "admin/blog-input";
    }

    @PostMapping(value = "/updateBlog/{id}")
    public String updateBlog(@Validated ShowBlog showBlog,RedirectAttributes attributes){
        int b = blogService.updateBlog(showBlog);
        if (b==0){
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/blogs";
    }


    @RequestMapping("/blogs/{id}/delete")
    public String delete(@PathVariable long id){
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }


//    @RequestMapping("/blog/search")
//    public String search(BlogSearch blogSearch, Model model,
//                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
//        List<Blog> blogBySearch = blogService.getBlogBySearch(blogSearch);
//        PageHelper.startPage(pageNum, 10);
//        PageInfo<Blog> pageInfo = new PageInfo<>(blogBySearch);
//        model.addAttribute("pageInfo", pageInfo);
//        return "/admin/blogs :: blogList";
//    }


}

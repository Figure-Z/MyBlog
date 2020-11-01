package com.zsq.myblog.web.blogs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.blog.admin.service.BlogService;
import com.zsq.myblog.blog.admin.service.TypeService;
import com.zsq.myblog.showVo.F_blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeIndexController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Long id, Model model){

        List<Type> types = typeService.getAllTypeAndBlog();
        if (id == -1) {
            id = types.get(0).getId();
        }

        model.addAttribute("types", types);

        PageHelper.startPage(pageNum, 10000);
        List<F_blogs> blogs = blogService.getByTypeId(id);
        PageInfo<F_blogs> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);

        return "types";
    }

}

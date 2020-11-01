package com.zsq.myblog.blog.admin.controller;


import com.zsq.myblog.blog.admin.entity.User;
import com.zsq.myblog.blog.admin.service.UserService;
import com.zsq.myblog.common.Result;
import com.zsq.myblog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public String login(@RequestParam String username,@RequestParam String password, HttpSession session, RedirectAttributes attributes){

        User userTemp = userService.checkUser(username, MD5Util.getMD5(password));

        if (userTemp!=null){
            userTemp.setPassword(null);
            session.setAttribute("user",userTemp);
            return "admin/blogback";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin/loginPage";
        }
    }


}

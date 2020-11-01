package com.zsq.myblog.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");//登出清空session
        return "redirect:/admin/loginPage";
    }

}

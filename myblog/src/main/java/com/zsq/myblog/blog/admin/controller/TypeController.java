package com.zsq.myblog.blog.admin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.blog.admin.service.TypeService;
import com.zsq.myblog.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //查询列表
    @RequestMapping("/types")
    public String blogList(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        String orderBy = "id desc"; //倒序排序
        PageHelper.startPage(pageNum,10,orderBy); //开启分页
        List<Type> b_types = typeService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(b_types); //封装进分页对象中
        model.addAttribute("pageInfo",pageInfo);

        return "admin/blogtypes";
    }

    //跳转新增页面
    @RequestMapping("/types/input")
    public String input(){
        return "admin/blogtype-input";
    }

    //新增分类
    @PostMapping(value = "/type_input",consumes = "application/json")
    @ResponseBody
    public Result<Type> save(@RequestBody Type type){

        Type typeTemp = typeService.getTypeByName(type.getName());
        if (typeTemp != null){
            return new Result<>(Result.ResultStatus.FAILED.status,"该分类已存在");
        }

        int t = typeService.saveType(type);
        if (t==0){
            return new Result<>(Result.ResultStatus.FAILED.status,"保存失败");
        }else {
            return new Result<>(Result.ResultStatus.SUCCESS.status,"保存成功");
        }

    }

    @RequestMapping("/types/{id}/update")
    public String update(@PathVariable long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/blogtype-update";
    }

    @PutMapping(value = "/type_update",consumes = "application/json")
    @ResponseBody
    public Result<Type> update(@RequestBody Type type) {

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            return new Result<>(Result.ResultStatus.FAILED.status,"不能修改为已有名称");
        }

        int t = typeService.updateType(type);
        if (t==0){
            return new Result<>(Result.ResultStatus.FAILED.status,"编辑失败");
        }else {
            return new Result<>(Result.ResultStatus.SUCCESS.status,"编辑成功");
        }
    }

    @RequestMapping("/types/{id}/delete")
    public String delete(@PathVariable long id){
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }
}

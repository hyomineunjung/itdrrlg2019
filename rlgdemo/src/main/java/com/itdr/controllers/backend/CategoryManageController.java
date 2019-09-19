package com.itdr.controllers.backend;

import com.itdr.common.ServerResponse;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("get_deep_category.do")
    //根据分类id查询所有子类(包括本身)
    private ServerResponse get_deep_category(Integer categoryId) {
        ServerResponse sr = categoryService.get_deep_category(categoryId);
        return sr;
    }
}

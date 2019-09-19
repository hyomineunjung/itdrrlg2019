package com.itdr.controllers;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;
import com.itdr.pojo.Users;
import com.itdr.services.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@RequestMapping("/manage/product/")
@Controller
@ResponseBody
public class ProductController {

    @Autowired
    private ProductService productService;


//    //通过商品id查询商品
//    @RequestMapping("cxOne.do")
//    @ResponseBody
//    public ServerResponse cxOne(Integer id, Model m){
//        ServerResponse sr = productService.cxOne(id);
////        System.out.println(p);
//        return sr;
//    }
//
//    //通过商品名模糊查询商品
//    @RequestMapping("cxMore.do")
//    @ResponseBody
//    public ServerResponse cxMore(String name, Model m){
//        ServerResponse sr = productService.cxMore(name);
//        return sr;
//    }
//
//    //增加商品
//    @RequestMapping("addOne.do")
//    @ResponseBody
//    public ServerResponse addOne(Product p, Model m){
//        ServerResponse sr = productService.insert(p);
//        return sr;
//    }

    //    1.产品搜索及动态排序List


    //1.产品搜索及动态排序List
    @RequestMapping("list.do")
    public ServerResponse list(Integer categoryId,
                               String keyword,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                               @RequestParam(value = "orderBy",required = false,defaultValue = "")String orderBy) {
        ServerResponse sr = productService.list(categoryId, keyword, pageNum, pageSize, orderBy);
        return sr;
    }

    //2.产品detail
    @RequestMapping("detail.do")
    public ServerResponse detail(Integer productId,
    @RequestParam(value = "is_new",required = false,defaultValue = "0") Integer is_new,
    @RequestParam(value = "is_hot",required = false,defaultValue = "0")Integer is_hot,
    @RequestParam(value = "is_banner",required = false,defaultValue = "0")Integer is_banner) {
//        Integer is_new //1为最新，0为否
//        Integer is_hot //1为最热，0为否
//        Integer is_banner //1为banner，0为否
        ServerResponse sr = productService.detail(productId,is_new,is_hot,is_banner);
        return sr;
    }

    //3.获取产品分类
    @RequestMapping("topcategory.do")
    public ServerResponse topcategory(Integer sid) {
        ServerResponse sr = productService.topcategory(sid);
        return sr;
    }
}

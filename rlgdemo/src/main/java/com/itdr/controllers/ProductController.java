package com.itdr.controllers;

import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.ResponseWrapper;
import java.util.List;

@RequestMapping("/manage/product/")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;


    //通过商品id查询商品
    @RequestMapping("cxOne.do")
    @ResponseBody
    public Product cxOne(Integer id, Model m){
        Product p = productService.cxOne(id);
//        System.out.println(p);
        return p;
    }

    //通过商品名模糊查询商品
    @RequestMapping("cxMore.do")
    @ResponseBody
    public List<Product> cxMore(String name, Model m){
        List<Product> p = productService.cxMore(name);
        return p;
    }

    //增加商品
    @RequestMapping("addOne.do")
    @ResponseBody
    public Integer addOne(Product p, Model m){
        Integer i= productService.insert(p);
        return i;
    }

    //
}

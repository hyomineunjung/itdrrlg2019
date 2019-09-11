package com.itdr.controllers;

import com.itdr.common.ServerResponse;
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
    public ServerResponse cxOne(Integer id, Model m){
        ServerResponse sr = productService.cxOne(id);
//        System.out.println(p);
        return sr;
    }

    //通过商品名模糊查询商品
    @RequestMapping("cxMore.do")
    @ResponseBody
    public ServerResponse cxMore(String name, Model m){
        ServerResponse sr = productService.cxMore(name);
        return sr;
    }

    //增加商品
    @RequestMapping("addOne.do")
    @ResponseBody
    public ServerResponse addOne(Product p, Model m){
        ServerResponse sr = productService.insert(p);
        return sr;
    }

    //
}

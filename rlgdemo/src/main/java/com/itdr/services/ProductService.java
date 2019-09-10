package com.itdr.services;

import com.itdr.pojo.Product;

import java.util.List;

public interface ProductService {

    //查询商品
    Product cxOne(Integer id);

    //根据商品名模糊查询
    List<Product> cxMore(String name);

    //增加商品
    int insert(Product record);
}

package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;

import java.util.List;

public interface ProductService {

    //查询商品
    ServerResponse cxOne(Integer id);

    //根据商品名模糊查询
    ServerResponse cxMore(String name);

    //增加商品
    ServerResponse insert(Product record);


}

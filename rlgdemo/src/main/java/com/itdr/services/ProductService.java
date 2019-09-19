package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;

import java.util.List;

public interface ProductService {

    //查询商品
    ServerResponse cxOne(Integer id);

    //根据商品名模糊查询
    ServerResponse cxMore(String name);



    //商品detail
    ServerResponse detail(Integer productId,Integer is_new,Integer is_hot,Integer is_banner);

    //动态排序
    ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);

    //获取产品分类
    ServerResponse topcategory(Integer sid);
}

package com.itdr.services.impl;

import com.itdr.common.ServerResponse;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    //根据id查询商品
    @Override
    public ServerResponse cxOne(Integer id) {
        Product p = productMapper.cxOne(id);
        ServerResponse sr = ServerResponse.successRS(200, p);
        return sr;
    }

    //根据商品名模糊查询
    @Override
    public ServerResponse cxMore(String name) {
        List<Product> p = productMapper.cxMore(name);
        ServerResponse sr = ServerResponse.successRS(200, p);
        return sr;
    }

    @Override
    public ServerResponse insert(Product record) {
        int i = productMapper.insert(record);
        ServerResponse sr = ServerResponse.successRS(200, i);
        return sr;
    }


}

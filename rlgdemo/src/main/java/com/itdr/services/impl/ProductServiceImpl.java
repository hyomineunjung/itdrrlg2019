package com.itdr.services.impl;

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
    public Product cxOne(Integer id) {
        Product p = productMapper.cxOne(id);
        return p;
    }

    //根据商品名模糊查询
    @Override
    public List<Product> cxMore(String name) {
        List<Product> p = productMapper.cxMore(name);
        return p;
    }

    @Override
    public int insert(Product record) {
        int i = productMapper.insert(record);
        return i;
    }


}

package com.itdr.mappers;

import com.itdr.pojo.Product;

import java.util.List;


public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);


    //自己写的
    //根据id查询商品
    Product cxOne(Integer id);

    //根据商品名模糊查询
    List<Product> cxMore(String name);

    //增加商品

}
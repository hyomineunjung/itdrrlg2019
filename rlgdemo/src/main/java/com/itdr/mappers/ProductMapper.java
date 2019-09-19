package com.itdr.mappers;

import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductMapper {

    //自己写的
    //根据id查询商品
    Product cxOne(Integer id);

    //根据商品名模糊查询
    List<Product> cxMore(String name);

    Product detail(@Param("productId") Integer productId,
                   @Param("is_new")Integer is_new,
                   @Param("is_hot")Integer is_hot,
                   @Param("is_banner")Integer is_banner);



    //增加商品

    //获取产品分类
    List<Category> topcategory(Integer sid);

    List<Product> selectByIdOrName(@Param("categoryId") Integer categoryId,
                                   @Param("keyword")String keyword,
                                   @Param("col")String col,
                                   @Param("order")String order);
}
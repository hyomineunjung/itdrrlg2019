package com.itdr.mappers;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);



    List<Category> get_deep_category(Integer categoryId);

    //获取产品分类
    List<Category> topcategory(Integer sid);
}
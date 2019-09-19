package com.itdr.services.impl;

import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.pojo.Category;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse get_deep_category(Integer categoryId) {
        if (categoryId == null || categoryId < 0) {
            return ServerResponse.defeatedRS("非法参数");
        }
        List<Integer> li = new ArrayList<>();
        getAll(categoryId,li);
        return ServerResponse.successRS(li);
    }

    private void getAll(Integer pid,List<Integer> li){
        List<Category> list = categoryMapper.get_deep_category(pid);
        if (list!=null && list.size()!=0){
            for (Category c:list){
                li.add(c.getId());
                getAll(c.getId(),li);
            }
        }
    }
}

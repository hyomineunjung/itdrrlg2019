package com.itdr.services;

import com.itdr.common.ServerResponse;

public interface CategoryService {

    //递归
    ServerResponse get_deep_category(Integer pid);

    //获取产品分类
//    ServerResponse topcategory(Integer sid);
}

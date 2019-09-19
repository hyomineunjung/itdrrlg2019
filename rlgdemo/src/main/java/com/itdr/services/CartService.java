package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.vo.CartVo2;

public interface CartService {

    //1.查询用户购物车情况
    ServerResponse<CartVo2> cx(Integer user_id);

    //2.购物车添加商品
    ServerResponse<CartVo2> add(Integer user_id, Integer productId, Integer count);

    //3.更新购物车某个产品数量
    ServerResponse<CartVo2> update(Integer user_id,Integer productId, Integer count);

    //4.移除购物车某个产品
    ServerResponse<CartVo2> delete_product(String productIds,Integer user_id);

    //5.购物车选中某个商品
    ServerResponse<CartVo2> select(Integer productId, Integer user_id);

    //6.购物车取消选中某个商品
    ServerResponse<CartVo2> un_select(Integer productId, Integer user_id);

    //7.查询在购物车里的产品数量
    ServerResponse<Integer> get_cart_product_count(Integer user_id);

    //8.购物车全选
    ServerResponse<CartVo2> select_all(Integer id,Integer check,Integer productId);
}

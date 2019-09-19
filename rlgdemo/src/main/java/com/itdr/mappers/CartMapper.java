package com.itdr.mappers;

import com.itdr.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    //1.购物车list列表
    List<Cart> cx(@Param("user_id") Integer user_id);

    //2.购物车添加商品
    Integer add(@Param("user_id")Integer user_id,@Param("productId")Integer productId,@Param("count")Integer count);

    //2.1 查询该用户购物车中是否包含该商品
    Cart cxs(@Param("user_id")Integer user_id,@Param("productId")Integer productId);


    //3.更新购物车某个产品数量
    Integer update(@Param("user_id")Integer user_id,@Param("productId")Integer productId,@Param("count")Integer count);

    //4.移除购物车某个产品
    Integer delete_product(@Param("productId") Integer productId,@Param("user_id")Integer user_id);

    //5.购物车选中某个商品
    Integer select(@Param("productId")Integer productId,@Param("user_id")Integer user_id);

    //6.购物车取消选中某个商品
    Integer un_select(@Param("productId")Integer productId,@Param("user_id")Integer user_id);

    //7.查询在购物车里的产品数量
    List get_cart_product_count(@Param("user_id")Integer user_id);

    //根据用户id查询所有购物数据
    List<Cart> selectByUid(Integer uid);

    //根据用户id查询购物车是否全选
    int selectByUidCheck(@Param("uid") Integer uid,@Param("uc") Integer uc);

    int select_all(@Param("uid") Integer id,@Param("check") Integer check,@Param("productId") Integer productId);
}
package com.itdr.services.impl;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CartMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.CartVo;
import com.itdr.pojo.vo.CartVo2;
import com.itdr.services.CartService;
import com.itdr.utils.BigDecimalUtils;
import com.itdr.utils.PoToVoUtil;
import com.itdr.utils.PropertiesUtil;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    //1.购物车list列表 通过登录状态的用户id去购物车表搜索
    @Override
    public ServerResponse<CartVo2> cx(Integer user_id) {
        List<Cart> li = cartMapper.cx(user_id);
        if (li == null) {
            return ServerResponse.defeatedRS("还没有选中任何商品哦~");
        }
        CartVo2 cartVo = getCartVo(user_id);
        return ServerResponse.successRS(cartVo);
    }


    private CartVo2 getCartVo(Integer uid) {
        CartVo2 cartVo2 = new CartVo2();
        //创建变量存储购物车
        BigDecimal cartTotalPrice = new BigDecimal("0");

        //用来存放CartProductVO对象的集合
        List<CartVo> cartProductVoList = new ArrayList<>();

        //根据用户Id查询该用户所有购物车信息
        List<Cart> li = cartMapper.selectByUid(uid);
        if (li.size() != 0) {
            //从购物信息集合中拿出每一条数据，根据其中的商品数据拿出需要的商品信息
            for (Cart cart : li) {
                //根据购物信息中的商品id查询商品信息
                Product p = productMapper.detail(cart.getProductId(), 0, 0, 0);
                //使用工具类进行封装
                CartVo c = PoToVoUtil.getOne(cart, p);

                //计算购物车总价
                if (c.getProductChecked() == Const.Cart.CHECK) {
                    cartTotalPrice = BigDecimalUtils.add(cartTotalPrice.doubleValue(), c.getProductTotalPrice().doubleValue());
                }
                //把对象放到集合中
                cartProductVoList.add(c);
            }
        }
        cartVo2.setCartVos(cartProductVoList);
        cartVo2.setAllChecked(this.checkAll(uid));
        cartVo2.setCartTotalPrice(cartTotalPrice);
        try {
            cartVo2.setImageHost(PropertiesUtil.getValue("imageHost"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cartVo2;
    }

    //判断用户购物车是否全选
    private boolean checkAll(Integer uid) {
        int i = cartMapper.selectByUidCheck(uid, Const.Cart.UNCHECK);
        if (i == 0) {
            //全选
            return true;
        } else {
            //没有全选
            return false;
        }
    }

    //2.购物车添加商品
    @Override
    public ServerResponse<CartVo2> add(Integer user_id, Integer productId, Integer count) {
        if (user_id == null || productId == null || count == null) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        //判断该用户购物车之中是否包含该商品
        Cart cxs = cartMapper.cxs(user_id, productId);
        //已存在的情况  就更新数量
        if (cxs != null) {
            count = count + cxs.getQuantity();
            Integer cxss = cartMapper.update(user_id, productId, count);
            if (cxss <= 0) {
                return ServerResponse.defeatedRS("更新数据失败");
            }
            List<Cart> li = cartMapper.cx(user_id);
            if (li == null) {
                return ServerResponse.defeatedRS("还没有选中任何商品哦~");
            }
            CartVo2 cartVo = getCartVo(user_id);
            return ServerResponse.successRS(cartVo);
        }
        //不存在的情况下 增加一条数据
        Integer i = cartMapper.add(user_id, productId, count);
        if (i <= 0) {
            return ServerResponse.defeatedRS("更新数据失败");
        }
        List<Cart> li = cartMapper.cx(user_id);
        if (li == null) {
            return ServerResponse.defeatedRS("还没有选中任何商品哦~");
        }
        CartVo2 cartVo = getCartVo(user_id);
        return ServerResponse.successRS(cartVo);
    }

    //3.更新购物车某个产品数量
    @Override
    public ServerResponse<CartVo2> update(Integer user_id, Integer productId, Integer count) {
        if (user_id == null || productId == null || count == null) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        Integer i = cartMapper.update(user_id, productId, count);
        if (i <= 0) {
            return ServerResponse.defeatedRS("更新数据失败");
        }
        List<Cart> li = cartMapper.cx(user_id);
        CartVo2 cartVo = getCartVo(user_id);
        return ServerResponse.successRS(cartVo);
    }

    //4.移除购物车某个产品
    @Override
    public ServerResponse<CartVo2> delete_product(String productIds, Integer user_id) {
        if (productIds == null || "".equals(productIds)) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        String[] s = productIds.split(",");
        for (String s1 : s) {
            Integer i = Integer.valueOf(s1);
            Integer ii = cartMapper.delete_product(i, user_id);
            if (ii <= 0) {
                return ServerResponse.defeatedRS("商品不存在");
            }
        }
        List<Cart> li = cartMapper.cx(user_id);
        CartVo2 cartVo = getCartVo(user_id);
        return ServerResponse.successRS(cartVo);
    }

    //5.购物车选中某个商品
    @Override
    public ServerResponse<CartVo2> select(Integer productId, Integer user_id) {
        if (productId == null) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        Integer i = cartMapper.select(productId, user_id);
        if (i <= 0) {
            return ServerResponse.defeatedRS("商品不存在");
        }
        List<Cart> li = cartMapper.cx(user_id);
        if (li == null) {
            return ServerResponse.defeatedRS("还没有选中任何商品哦~");
        }
        CartVo2 cartVo = getCartVo(user_id);
        return ServerResponse.successRS(cartVo);
    }

    //6.购物车取消选中某个商品
    @Override
    public ServerResponse<CartVo2> un_select(Integer productId, Integer user_id) {
        if (productId == null) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        Integer i = cartMapper.un_select(productId, user_id);
        if (i <= 0) {
            return ServerResponse.defeatedRS("商品不存在");
        }
        List<Cart> li = cartMapper.cx(user_id);
        if (li == null) {
            return ServerResponse.defeatedRS("还没有选中任何商品哦~");
        }
        CartVo2 cartVo = getCartVo(user_id);
        return ServerResponse.successRS(cartVo);
    }


    //7.查询在购物车里的产品数量
    @Override
    public ServerResponse<Integer> get_cart_product_count(Integer user_id) {
        List li = cartMapper.get_cart_product_count(user_id);
        if (li == null) {
            return ServerResponse.defeatedRS("出现异常");
        }
        return ServerResponse.successRS(li.size());
    }

    //改变购物车商品选中状态
    @Override
    public ServerResponse<CartVo2> select_all(Integer id,Integer check,Integer productId) {
        if (check==null){
            return ServerResponse.defeatedRS("非法参数");
        }
        int a = cartMapper.select_all(id,check,null);
        CartVo2 cartVo = getCartVo(id);
        return ServerResponse.successRS(cartVo);
    }

}

package com.itdr.utils;

import com.itdr.common.Const;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.CartVo;
import com.itdr.pojo.vo.ProductVO;

import java.io.IOException;
import java.math.BigDecimal;

public class PoToVoUtil {
    public static ProductVO ProductToProductVo(Product p) throws IOException {
        ProductVO pp = new ProductVO();
        pp.setId(p.getId());
        pp.setCategoryId(p.getCategoryId());
        pp.setCreateTime(p.getCreateTime());
        pp.setDetail(p.getDetail());
        pp.setIsBanner(p.getIsBanner());
        pp.setIsHot(p.getIsHot());
        pp.setIsNew(p.getIsNew());
        pp.setImageHost(PropertiesUtil.getValue("imageHost"));
        pp.setMainImage(p.getMainImage());
        pp.setName(p.getName());
        pp.setPrice(p.getPrice());
        pp.setStatus(p.getStatus());
        pp.setStock(p.getStock());
        pp.setSubImages(p.getSubImages());
        pp.setSubtitle(p.getSubtitle());
        pp.setUpdateTime(p.getUpdateTime());
        return pp;
    }

    public static CartVo getOne(Cart cart, Product p) {
        //封装CartVo
        CartVo c = new CartVo();
        c.setId(cart.getId());
        c.setUserId(cart.getUserId());
        c.setProductId(cart.getProductId());
        c.setQuantity(cart.getQuantity());
        c.setProductChecked(cart.getChecked());

        //查询到的商品不能为空
        if (p != null) {
            c.setName(p.getName());
            c.setSubtitle(p.getSubtitle());
            c.setMainImage(p.getMainImage());
            c.setPrice(p.getPrice());
            c.setStock(p.getStock());
            c.setStatus(p.getStatus());

        }

        Integer count = 0;

        //处理库存问题
        if (cart.getQuantity() <= p.getStock()) {
            count = cart.getQuantity();
            c.setLimitQuantity(Const.Cart.LimitQuantitySuccess);
        } else {
            count = p.getStock();
            c.setLimitQuantity(Const.Cart.LimitQuantityFaild);
        }
        c.setQuantity(count);

        BigDecimal productTotalPrice = BigDecimalUtils.mul(p.getPrice().doubleValue(), cart.getQuantity());
        c.setProductTotalPrice(productTotalPrice);

        return c;
    }
}

package com.itdr.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVo2 {
    private List<CartVo> cartVos;
    private boolean allChecked;
    private BigDecimal cartTotalPrice;
    private String imageHost;

    public List<CartVo> getCartVos() {
        return cartVos;
    }

    public void setCartVos(List<CartVo> cartVos) {
        this.cartVos = cartVos;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}

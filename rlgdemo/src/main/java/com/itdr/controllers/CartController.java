package com.itdr.controllers;


import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@RequestMapping("/manage/cart/")
@Controller
@ResponseBody
public class CartController {

    @Autowired
    private CartService cartService;

    //√
    //1.购物车list列表
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.cx(u.getId());
        return sr;
    }

    //√
    //2.购物车添加商品(如果是有同样的商品反复加 应该只有一个才对)
    //当商品号传进来的时候 应该先 查询 以前该用户的购物车中是否包含该商品 如果包含则改变数量 不包含就多一条数据
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(Integer productId,Integer count,HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.add(u.getId(),productId,count);
        return sr;
    }

    //√
    //3.更新购物车某个产品数量
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(Integer productId,Integer count,HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.update(u.getId(),productId,count);
        return sr;
    }

    //√
    //4.移除购物车某个产品
    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse delete_product(String productIds,HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.delete_product(productIds,u.getId());
        return sr;
    }
    //√
    //5.购物车选中某个商品
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(Integer productId,HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.select(productId,u.getId());
        return sr;
    }
    //√
    //6.购物车取消选中某个商品
    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse un_select(Integer productId,HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.un_select(productId,u.getId());
        return sr;
    }

    //7.查询在购物车里的产品数量
    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse get_cart_product_count(HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.get_cart_product_count(u.getId());
        return sr;
    }

    //8.购物车全选
    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse select_all(HttpSession session,Integer check,Integer productId) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.select_all(u.getId(),check,productId);
        return sr;
    }

    //9.购物车取消全选
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse un_select_all(HttpSession session,Integer check,Integer productId) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u == null) {
            return ServerResponse.defeatedRS("用户未登录，请登录后操作");
        }
        ServerResponse sr = cartService.select_all(u.getId(),check,productId);
        return sr;
    }
}

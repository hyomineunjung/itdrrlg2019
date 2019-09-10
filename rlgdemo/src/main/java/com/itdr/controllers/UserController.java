package com.itdr.controllers;

import com.itdr.common.ResponseCode;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/manage/user/")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //用户登录
    @RequestMapping("login.do")
    @ResponseBody
    public ResponseCode login(String username,String password){
        ResponseCode rs = userService.login(username, password);
        return rs;
    }


    //用户注册
    @RequestMapping("register.do")
    @ResponseBody
    public ResponseCode register(Users u){
        ResponseCode rs = userService.register(u);
        return rs;
    }

    //忘记密码
    @RequestMapping("forget_get_question.do")
    @ResponseBody
    public ResponseCode forget(String username){
        ResponseCode rs = userService.forget(username);
        return rs;
    }

    //提交答案 因为令牌问题搁浅
    //通过session获取登录状态
    @RequestMapping("ceshi.do")
    @ResponseBody
    public ResponseCode ceshi(String username, String password, HttpSession session){
        ResponseCode rs = userService.login(username, password);
        session.setAttribute("user",rs.getData());
        Users user = (Users) session.getAttribute("user");
        System.out.println(user);
        return rs;
    }

    //忘记密码的重新设置密码  同令牌问题搁浅


}

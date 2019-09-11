package com.itdr.controllers;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("/manage/user/")
@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    //√
    //用户登录
    @RequestMapping("login.do")
    public ServerResponse login(String username, String password, HttpSession session) {
        ServerResponse sr = userService.login(username, password);
        if (sr.isSuccess()) {
            session.setAttribute(Const.LOGINUSER, sr.getData());
            Users u2 = (Users) sr.getData();
            u2.setPassword("");
            sr.setData(u2);
            return sr;
        }
        return sr;
    }

    //√
    //用户注册
    @RequestMapping("register.do")
    public ServerResponse register(Users u) {
        ServerResponse sr = userService.register(u);
        return sr;
    }

    //√
    //检查用户名是否有效
    @RequestMapping("check_valid.do")
    public ServerResponse check_valid(String str, String type) {
        ServerResponse sr = userService.check_valid(str, type);
        return sr;
    }

    //√
    //获取登录信息
    @RequestMapping("get_user_info.do")
    public ServerResponse<Users> getUserInfo(HttpSession session) {
        Users user = (Users) session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }
        return ServerResponse.successRS(user);
    }

    //√
    //获取当前登录用户的详细信息
    @RequestMapping("get_inforamtion.do")
    public ServerResponse<Users> get_inforamtion(HttpSession session) {
        Users user = (Users) session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }
        ServerResponse sr = userService.cxyh(user);
        return sr;
    }
    //√
    //登录状态更新个人信息
    @RequestMapping("update_information.do")
    public ServerResponse<Users> update_information(Users u, HttpSession session) {
        Users user = (Users) session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        ServerResponse sr = userService.update_information(u);
        return sr;
    }


    //√
    //忘记密码
    @RequestMapping("forget_get_question.do")
    public ServerResponse forget_get_question(String username, HttpSession session) {
        ServerResponse sr = userService.forget_get_question(username);
        return sr;
    }

    //√
    //提交问题答案
    @RequestMapping("forget_check_answer.do")
    public ServerResponse forget_check_answer(String username, String question, String answer, HttpSession session) {
        ServerResponse sr = userService.forget_check_answer(username, question, answer);
        return sr;
    }

    //√
    //忘记密码重设密码
    @RequestMapping("forget_reset_password.do")
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        ServerResponse sr = userService.forget_reset_password(username, passwordNew, forgetToken);
        return sr;
    }


    //√
    //登录状态重置密码
    @RequestMapping("reset_password.do")
    public ServerResponse reset_password(String passwordOld, String passwordNew,HttpSession session) {
        Users u = (Users) session.getAttribute(Const.LOGINUSER);
        if (u==null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }else {
            ServerResponse sr = userService.reset_password(u, passwordOld, passwordNew);
            return sr;
        }
    }

    //退出登录
    @RequestMapping("logout.do")
    public ServerResponse<Users> logout(HttpSession session) {
        session.removeAttribute(Const.LOGINUSER);
        return ServerResponse.successRS("退出成功");
    }
}

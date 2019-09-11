package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;

public interface UserService {

    //登录
    ServerResponse login(String name, String password);


    //用户注册
    ServerResponse register(Users u);

    //忘记密码
    ServerResponse forget(String username);

    ServerResponse check_valid(String str, String type);


    ServerResponse forget_get_question(String username);

    ServerResponse forget_check_answer(String username, String question, String answer);

    //查询用户
    ServerResponse cxyh(Users u);

    ServerResponse update_information(Users u);

    ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken);

    ServerResponse reset_password(Users u, String passwordOld, String passwordNew);

}

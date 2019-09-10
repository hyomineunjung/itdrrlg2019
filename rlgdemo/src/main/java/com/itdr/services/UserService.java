package com.itdr.services;

import com.itdr.common.ResponseCode;
import com.itdr.pojo.Users;

public interface UserService {

    //登录
    ResponseCode login(String name, String password);


    //用户注册
    ResponseCode register(Users u);

    //忘记密码
    ResponseCode forget(String username);

}

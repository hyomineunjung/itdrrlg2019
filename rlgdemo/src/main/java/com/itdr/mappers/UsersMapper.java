package com.itdr.mappers;

import com.itdr.pojo.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    //用户注册
    int insert(Users record);
    //注册验证
    Users register_yz1(String email);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);


    //自己写的

    //登录验证
    Users login(String username, String password);

    //查询是否存在该用户
    Users login_yz1(String username);

    //查询密码是否正确
    String login_yz2(String username);

    //忘记密码
    String forget(String username);

}
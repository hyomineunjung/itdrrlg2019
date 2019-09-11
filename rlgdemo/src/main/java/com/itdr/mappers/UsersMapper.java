package com.itdr.mappers;

import com.itdr.pojo.Users;
import org.apache.ibatis.annotations.Param;

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

    //根据用户名或者邮箱查询用户
    int check_valid(@Param("str") String str, @Param("type") String type);

    //根据用户名查找用户密码问题
    String selectByUsername(String username);

    //提交问题答案
    int selectByUsernameAndQuestionAndAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int selectByEmailAndId(@Param("email") String email,@Param("id") Integer id);

    //根据用户名去更新密码
    int updateByUsernameAndPassword(@Param("username") String username, @Param("password") String passwordNew);

    //根据用户id查询密码是否正确
    int selectByIdAndPassword(@Param("id") Integer id, @Param("passwordOld") String passwordOld);
}
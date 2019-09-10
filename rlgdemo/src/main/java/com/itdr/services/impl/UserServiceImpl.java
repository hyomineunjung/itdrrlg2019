package com.itdr.services.impl;

import com.itdr.common.ResponseCode;
import com.itdr.mappers.UsersMapper;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ResponseCode rs;

    //用户登录
    @Override
    public ResponseCode login(String name, String password) {
        //检验输入的用户名是否为空
        if (name==null){
            rs=ResponseCode.defeatedRS(100,"用户名不能为空");
            return rs;
        }

        //检验输入的密码是否为空
        if (password==null){
            rs=ResponseCode.defeatedRS(100,"密码不能为空");
            return rs;
        }

        //验证是否存在该用户
        Users u1 = usersMapper.login_yz1(name);
        if (u1==null){
            rs=ResponseCode.defeatedRS(101,"用户名不存在");
            return rs;
        }

        //验证密码是否正确
        String mima = usersMapper.login_yz2(name);
        if (mima.equals(password)){
            //如果相等执行登录
            Users u = usersMapper.login(name, password);
            rs=ResponseCode.successRS(200,u);
            return rs;
        }

        rs=ResponseCode.defeatedRS(1,"密码错误");
        return rs;
    }

    //用户注册
    @Override
    public ResponseCode register(Users u) {
        //验证用户输入信息是否存在空值
        if (u.getUsername()==null||u.getPassword()==null||u.getEmail()==null||u.getPhone()==null||u.getQuestion()==null||u.getAnswer()==null){
            rs=ResponseCode.defeatedRS(100,"注册信息不能为空");
            return rs;
        }
        //执行注册程序
        //先检验用户名是否已存在
        Users uu = usersMapper.login_yz1(u.getUsername());
        if (uu==null){
            //检验邮箱是否已存在
            Users uuu = usersMapper.register_yz1(u.getEmail());
            if (uuu==null){
                //都没有问题 执行注册程序
                int i = usersMapper.insert(u);
                if (i==1){
                    rs=ResponseCode.successRS(0,"用户注册成功");
                    return rs;
                }
            }else {
                rs=ResponseCode.defeatedRS(2,"邮箱已注册");
                return rs;
            }
        }
        rs=ResponseCode.defeatedRS(1,"用户已存在");
        return rs;
    }

    //忘记密码
     @Override
        public ResponseCode forget(String username) {
        //检验用户输入的值是否为空
        if (username==null){
            rs=ResponseCode.defeatedRS(100,"用户名不能为空");
            return rs;
        }
        //检验是否存在这个用户
         Users u = usersMapper.login_yz1(username);
        if (u==null){
            rs=ResponseCode.defeatedRS(101,"用户名不存在");
            return rs;
        }
        //检验该用户是否设置了找回密码的问题
         String s = usersMapper.forget(username);
         if (s==null){
             rs=ResponseCode.defeatedRS(1,"该用户未设置找回密码问题");
             return rs;
         }
         rs=ResponseCode.successRS(0,s);
         return rs;
     }


}

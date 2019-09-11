package com.itdr.services.impl;

import com.itdr.common.ServerResponse;
import com.itdr.common.TokenCache;
import com.itdr.mappers.UsersMapper;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import com.itdr.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;


    //用户登录
    @Override
    public ServerResponse login(String name, String password) {
        //检验输入的用户名是否为空
        if (name == null || name.equals("")) {
            ServerResponse sr = ServerResponse.defeatedRS(100, "用户名不能为空");
            return sr;
        }

        //检验输入的密码是否为空
        if (password == null || password.equals("")) {
            ServerResponse sr = ServerResponse.defeatedRS(100, "密码不能为空");
            return sr;
        }

        //验证是否存在该用户
        Users u1 = usersMapper.login_yz1(name);
        if (u1 == null) {
            ServerResponse sr = ServerResponse.defeatedRS(101, "用户名不存在");
            return sr;
        }
        //md5加密
        String md5Code = MD5Utils.getMD5Code(password);

        //验证密码是否正确
        String mima = usersMapper.login_yz2(name);
        if (mima.equals(md5Code)) {
            //如果相等执行登录
            Users u = usersMapper.login(name, md5Code);
            ServerResponse sr = ServerResponse.successRS(200, u);
            return sr;
        }

        ServerResponse sr = ServerResponse.defeatedRS(1, "密码错误");
        return sr;
    }

    //用户注册
    @Override
    public ServerResponse register(Users u) {
        //验证用户输入信息是否存在空值
        if (u.getUsername() == null || u.getPassword() == null || u.getEmail() == null || u.getPhone() == null || u.getQuestion() == null || u.getAnswer() == null) {
            ServerResponse sr = ServerResponse.defeatedRS(100, "注册信息不能为空");
            return sr;
        }
        //执行注册程序
        //先检验用户名是否已存在
        Users uu = usersMapper.login_yz1(u.getUsername());
        if (uu == null) {
            //检验邮箱是否已存在
            Users uuu = usersMapper.register_yz1(u.getEmail());
            if (uuu == null) {
                //都没有问题 执行注册程序
                u.setPassword(MD5Utils.getMD5Code(u.getPassword()));
                int i = usersMapper.insert(u);
                if (i == 1) {
                    ServerResponse sr = ServerResponse.successRS(200, "用户注册成功");
                    return sr;
                }
            } else {
                ServerResponse sr = ServerResponse.defeatedRS(2, "邮箱已注册");
                return sr;
            }
        }
        ServerResponse sr = ServerResponse.defeatedRS(1, "用户已存在");
        return sr;
    }

    //忘记密码
    @Override
    public ServerResponse forget(String username) {
        //检验用户输入的值是否为空
        if (username == null) {
            ServerResponse sr = ServerResponse.defeatedRS(100, "用户名不能为空");
            return sr;
        }
        //检验是否存在这个用户
        Users u = usersMapper.login_yz1(username);
        if (u == null) {
            ServerResponse sr = ServerResponse.defeatedRS(101, "用户名不存在");
            return sr;
        }
        //检验该用户是否设置了找回密码的问题
        String s = usersMapper.forget(username);
        if (s == null) {
            ServerResponse sr = ServerResponse.defeatedRS(1, "该用户未设置找回密码问题");
            return sr;
        }
        ServerResponse sr = ServerResponse.successRS(0, s);
        return sr;
    }

    //检查用户名或邮箱是否存在
    @Override
    public ServerResponse check_valid(String str, String type) {
        if (str == null || str.equals("")) {
            ServerResponse sr = ServerResponse.defeatedRS(100, "参数不能为空");
            return sr;
        }

        //检验输入的密码是否为空
        if (type == null || type.equals("")) {
            ServerResponse sr = ServerResponse.defeatedRS(100, "参数类型不能为空");
            return sr;
        }

        int i = usersMapper.check_valid(str, type);
        if (i > 0 && type.equals("username")) {
            return ServerResponse.defeatedRS("用户名已经存在！");
        }
        if (i > 0 && type.equals("email")) {
            return ServerResponse.defeatedRS("邮箱已经存在！");
        }
        return ServerResponse.successRS(200, null, "校验成功！");
    }

    //忘记密码获取问题
    @Override
    public ServerResponse forget_get_question(String username) {
        if (username == null || username.equals("")) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        int i = usersMapper.check_valid(username, username);
        if (i == 0) {
            return ServerResponse.defeatedRS("用户名不存在");
        }
        String question = usersMapper.selectByUsername(username);
        if (question == null || "".equals(question)) {
            return ServerResponse.defeatedRS("该用户没有设置问题");
        }
        return ServerResponse.successRS(question);
    }

    //提交问题答案
    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        if (username == null || "".equals(username)) {
            return ServerResponse.defeatedRS("用户名不能为空");
        }
        if (question == null || "".equals(question)) {
            return ServerResponse.defeatedRS("问题不能为空");
        }
        if (answer == null || "".equals(answer)) {
            return ServerResponse.defeatedRS("答案不能为空");
        }
        int i = usersMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (i <= 0) {
            return ServerResponse.defeatedRS("问题答案错误");
        }
        //产生随机令牌
        String token = UUID.randomUUID().toString();
        TokenCache.set("token_" + username, token);
        return ServerResponse.successRS(token);
    }

    //查询用户
    @Override
    public ServerResponse cxyh(Users u) {
        Users uu = usersMapper.selectByPrimaryKey(u.getId());
        if (uu == null) {
            return ServerResponse.defeatedRS("用户不存在");
        }
        uu.setPassword("");
        return ServerResponse.successRS(uu);
    }

    //登录状态更新个人信息
    @Override
    public ServerResponse update_information(Users u) {

        int i = usersMapper.selectByEmailAndId(u.getEmail(), u.getId());
        if (i > 0) {
            return ServerResponse.defeatedRS("用户邮箱已存在");
        }
        int i1 = usersMapper.updateByPrimaryKeySelective(u);
        if (i1 <= 0) {
            return ServerResponse.defeatedRS("更新失败");
        }
        return ServerResponse.successRS("更新个人信息成功");
    }

    //忘记密码重设密码
    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        if (username == null || "".equals(username)) {
            return ServerResponse.defeatedRS("用户名不能为空");
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS("问题不能为空");
        }
        if (forgetToken == null || "".equals(forgetToken)) {
            return ServerResponse.defeatedRS("Token已失效");
        }

        //判断缓存中的Token

        String token = TokenCache.get("token_" + username);
        if (token == null || "".equals(token)) {
            return ServerResponse.defeatedRS("Token过期了");
        }
        if (!token.equals(forgetToken)) {
            return ServerResponse.defeatedRS("非法的Token");
        }
        String md5Code=MD5Utils.getMD5Code(passwordNew);
        int i = usersMapper.updateByUsernameAndPassword(username, md5Code);
        if (i <= 0) {
            return ServerResponse.defeatedRS("修改密码失败");
        }
        return ServerResponse.successRS("修改密码成功");
    }

    //登录状态修改密码
    @Override
    public ServerResponse reset_password(Users u, String passwordOld, String passwordNew) {
        if (passwordOld == null || "".equals(passwordOld)) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        String md5Codeold=MD5Utils.getMD5Code(passwordOld);
        int i = usersMapper.selectByIdAndPassword(u.getId(), md5Codeold);
        if (i<=0){
            return ServerResponse.defeatedRS("旧密码输入错误");
        }
        String md5Codenew=MD5Utils.getMD5Code(passwordNew);
        int i1 = usersMapper.updateByUsernameAndPassword(u.getUsername(), md5Codenew);
        if (i1<=0){
            return ServerResponse.defeatedRS("密码更新失败");
        }
        return ServerResponse.successRS("密码更新成功");
    }

    //提交问题答案

}

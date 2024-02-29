package com.mengchu.controller;


import com.mengchu.pojo.*;
import com.mengchu.service.UserService;
import com.mengchu.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService service;

    @RequestMapping("/login")
    public Result login(@RequestBody User user){
        log.info("用户登陆，{}", user.getUsername());
        User u = service.login(user);
        if (u != null){
            //登陆成功
            //设置jwt载荷
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",u.getId());
            claims.put("username",u.getUsername());
            claims.put("nickname",u.getNickname());
            claims.put("description",u.getDescription());
            claims.put("collection",u.getCollection());
            //生成jwt令牌
            String jwt = JwtUtil.generateJwt(claims);
            //封装登陆信息
            Login login = new Login(u.getId(), jwt);
            return new Result(Code.Login_OK, Message.LOGIN_OK, login);
        }
        //登陆失败
        return new Result(Code.LOGIN_ERR, Message.LOGIN_ERR, null);
    }
}

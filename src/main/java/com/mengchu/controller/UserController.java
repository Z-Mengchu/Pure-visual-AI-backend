package com.mengchu.controller;

import com.mengchu.pojo.Code;
import com.mengchu.pojo.Message;
import com.mengchu.pojo.Result;
import com.mengchu.pojo.User;
import com.mengchu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public Result save(@RequestBody User user){
        log.info("保存用户，{}", user);
        boolean flag = service.saveUser(user);
        return new Result(flag ? Code.USER_SAVE_OK : Code.USER_SAVE_ERR,
                flag ? Message.USER_SAVE_OK : Message.USER_SAVE_ERR, null);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除用户，{}", id);
        boolean flag = service.delete(id);
        return new Result(flag ? Code.USER_DELETE_OK : Code.USER_DELETE_ERR,
                flag ? Message.USER_DELETE_OK : Message.USER_DELETE_ERR, null);
    }

    @PutMapping
    public Result update(@RequestBody User user){
        log.info("修改用户信息，{}", user);
        boolean flag = service.update(user);
        return new Result(flag ? Code.USER_UPDATE_OK : Code.USER_UPDATE_ERR,
                flag ? Message.USER_UPDATE_OK : Message.USER_UPDATE_ERR, null);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询用户，{}", id);
        User user = service.selectById(id);
        return new Result(user != null ? Code.USER_SELECT_OK : Code.USER_SELECT_ERR,
                user != null ? Message.USER_SELECT_OK : Message.USER_SELECT_ERR, user);
    }
}

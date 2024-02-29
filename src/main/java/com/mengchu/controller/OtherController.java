package com.mengchu.controller;

import com.mengchu.mapper.WorkMapper;
import com.mengchu.pojo.Code;
import com.mengchu.pojo.Message;
import com.mengchu.pojo.Result;
import com.mengchu.pojo.Work;
import com.mengchu.service.CollectionService;
import com.mengchu.service.UserService;
import com.mengchu.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
public class OtherController {
    @Autowired
    private UserService userService;
    @Autowired
    private WorkService workService;
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/upload")
    public Result upload(Integer id, MultipartFile image) throws IOException {
        log.info("id,{}", id);
        if(image == null){
            return new Result(Code.CHANGE_IMAGE_ERR, Message.CHANGE_IMAGE_ERR, null);
        }
        String address = userService.updateImage(id, image);
        return new Result(Code.CHANGE_IMAGE_OK, Message.CHANGE_IMAGE_OK, address);
    }

    @PutMapping("/likes/{id}")
    public Result like(@PathVariable Integer id){
        boolean flag = workService.like(id);
        return flag ? Result.success() : Result.error();
    }

    @PostMapping("/collect")
    public Result collect(Integer uid, Integer pid){
        boolean flag = collectionService.collect(uid, pid);
        return flag ? Result.success() : Result.error();
    }
}

package com.mengchu.controller;

import com.mengchu.exception.BusinessException;
import com.mengchu.exception.SystemException;
import com.mengchu.pojo.Code;
import com.mengchu.pojo.Message;
import com.mengchu.pojo.Result;
import com.mengchu.pojo.Work;
import com.mengchu.service.WorkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/works")
public class WorkController {
    @Autowired
    private WorkService service;

    @GetMapping
    public Result selectAll() {
        List<Work> works;
        try {
            works = service.selectAll();
            log.info("查询所有作品，{}", works);
        } catch (Exception e) {
            log.error("查询所有作品出错");
            e.printStackTrace();
            throw new SystemException(Code.SYSTEM_TIMEOUT_ERR, "服务器访问超时，请重试！");
        }
        return new Result(Code.WORK_SELECT_OK, Message.WORK_SELECT_OK, works);
    }

    @PostMapping
    public Result saveWork(@RequestBody Work work) {
        boolean flag = service.saveWork(work);
        log.info("发布作品，{}", work);
        return new Result(flag ? Code.WORK_SAVE_OK : Code.WORK_SAVE_ERR,
                flag ? Message.WORK_SAVE_OK : Message.WORK_SAVE_ERR, null);
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        if (id <= 0){
            throw new BusinessException(Code.BUSINESS_ERR, "没这个id，别搞了");
        }
        Work work = service.selectById(id);
        log.info("查询作品，{}", work);
        return new Result(work != null ? Code.WORK_SELECT_OK : Code.WORK_SELECT_ERR,
                work != null ? Message.WORK_SELECT_OK : Message.WORK_SELECT_ERR, work);
    }

    @PostMapping("/modeling")
    public Result modeling(MultipartFile[] image) throws IOException, ExecutionException, InterruptedException {
        log.info("/modeling被访问");
        if (image == null){
            return new Result(Code.WORK_MODELING_ERR, Message.WORK_MODELING_ERR, null);
        }
        UUID uuid = service.modeling(image);
        return new Result(Code.WORK_MODELING_OK, Message.WORK_MODELING_OK, uuid);
    }

    @GetMapping("/get")
    public Result getModel(UUID taskId) throws IOException, ExecutionException, InterruptedException {
        log.info("/get被访问");
        String address = service.getModel(taskId);
        if ("Modeling".equals(address)){
            return new Result(Code.GET_MODEL_ERR, Message.GET_MODEL_ERR, null);
        }
        return new Result(Code.GET_MODEL_OK, Message.GET_MODEL_OK, address);
    }

}

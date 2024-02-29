package com.mengchu.demo;

import com.mengchu.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;


@Slf4j
@RestController
public class demoController {
    ThreadPoolExecutor pool = new ThreadPoolExecutor(
            3,//核心线程数量   不能小于0
            17,//最大线程数量   不能小于0
            60,//空闲线程最大存活时间  不能小于0
            TimeUnit.SECONDS,//时间单位   用TimeUnit指定
            new ArrayBlockingQueue<>(2),//任务队列
            Executors.defaultThreadFactory(),//创建线程工厂
            new ThreadPoolExecutor.AbortPolicy()//任务的拒绝策略
    );
    Map<UUID, FutureTask<Integer>> map = new HashMap<>();

    @PostMapping("/post")
    public Result demo1(Integer num) {
        log.info("post被访问");
        DemoCallable mc = new DemoCallable(num);
        FutureTask<Integer> ft = new FutureTask<Integer>(mc);
        pool.submit(ft);
        UUID uuid = UUID.randomUUID();
        map.put(uuid, ft);
        return Result.success(uuid);
    }

    @GetMapping ("/get")
    public Result demo2(UUID uuid) throws ExecutionException, InterruptedException {
        log.info("get被访问");
        FutureTask<Integer> ft = map.get(uuid);
        if (ft.isDone()) {
            return Result.success(ft.get());
        }
        return Result.success();
    }

}

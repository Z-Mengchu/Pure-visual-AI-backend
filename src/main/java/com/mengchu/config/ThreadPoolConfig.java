package com.mengchu.config;

import com.mengchu.callable.ModelCallable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService threadPoolExecutor(){
        return new ThreadPoolExecutor(
                3,//核心线程数量
                17,//最大线程数量
                20,//空闲线程最大存活时间
                TimeUnit.MINUTES,//时间单位
                new LinkedBlockingDeque<>(),//任务队列
                Executors.defaultThreadFactory(),//创建线程工厂
                new ThreadPoolExecutor.AbortPolicy()//任务的拒绝策略
        );
    }
}

package com.mengchu.callable;

import com.mengchu.pojo.Modeling;
import com.mengchu.pojo.Rems;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class ModelCallable implements Callable<String> {
    String domain;

    public ModelCallable(String domain) {
        this.domain = domain;
    }

    @Override
    public String call() throws ExecutionException, InterruptedException {
        String rootPath = "http://127.0.0.1:1314";
        //获取restTemplate实例
        RestTemplate restTemplate = new RestTemplate();
        String url1 = rootPath + "/task";
        //设置请求参数
        Modeling modeling = new Modeling(domain, new Rems());
        //向python端发送post请求
        String response = restTemplate.postForObject(url1, modeling, String.class);
        if ("422".equals(response)) {
            //发送失败响应422
            return null;
        }
        String url2 = rootPath + "/task/" + response.substring(1, response.length() - 1);
        String url3 = url2 + "/path";

        //向python端定时发送get请求
        //使用Callable获得返回值
        //设置定时任务
        TimerCallable task = new TimerCallable(restTemplate, url3);

        FutureTask<String> futureTask = new FutureTask<>(task);
        futureTask.run();

        //返回值是建模文件存放的地址
        //使用get方法使该线程阻塞直到执行完毕
        String result = futureTask.get();
        return result.substring(1, result.length() - 1);
    }
}


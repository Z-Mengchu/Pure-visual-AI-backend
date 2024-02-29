package com.mengchu.callable;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class TimerCallable implements Callable<String> {
    RestTemplate restTemplate;
    String url;

    public TimerCallable (RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public String call() throws InterruptedException {
        while (true) {
            //获取返回状态码
            String result = restTemplate.getForObject(url, String.class);
            System.out.println("result:" + result);
            //使用线程休眠来做定时任务
            //两分钟发一次请求
            Thread.sleep(1000);
            //不是404说明建模已经完成
            if (result != null && !result.equals("404")) {
                //返回值是建模文件存放的地址
                return result;
            }
        }
    }
}

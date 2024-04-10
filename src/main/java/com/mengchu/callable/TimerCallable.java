package com.mengchu.callable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@Slf4j
public class TimerCallable implements Callable<String> {
    RestTemplate restTemplate;
    String url;

    public TimerCallable(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public String call() throws InterruptedException {
        while (true) {
            //获取返回状态码
            String result = restTemplate.getForObject(url, String.class);
            log.info("任务状态码 result:{}", result);
            //一分钟发一次请求
            //返回-1说明建模已经完成
            if (result != null && result.equals("-1")) {
                //返回值是建模文件存放的地址
                String url2 = url + "/path";
                return restTemplate.getForObject(url2, String.class);
            }
            //使用线程休眠来做定时任务
            Thread.sleep(60000);
        }
    }
}

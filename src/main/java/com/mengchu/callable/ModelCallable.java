package com.mengchu.callable;

import com.mengchu.pojo.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class ModelCallable implements Callable<String> {
    File zip;

    public ModelCallable(File zip) {
        this.zip = zip;
    }

    @Override
    public String call() {
        String result = null;
        try {
//            String rootPath = "http://193.112.98.244:6006";
            String rootPath = "http://localhost:6006";
            //获取restTemplate实例
            RestTemplate restTemplate = new RestTemplate();
            String url1 = rootPath + "/task";
            //要发送的file对象
            Resource resource = new FileSystemResource(zip);
            // 创建MultiValueMap来存储表单参数
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            // 添加文件资源
            body.add("images", resource);
            // 设置HTTP头部信息
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            // 创建HttpEntity对象，包含表单数据和头部信息
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            //向python端发送post请求
            String response = restTemplate.postForObject(url1, entity, String.class);
            if ("422".equals(response)) {
                //发送失败响应422
                return null;
            }
            log.info("/task, response,{}", response);
            String url2 = rootPath + "/task/" + response.substring(1, response.length() - 1);
            //设置定时任务 (向python端定时发送get请求)
            TimerCallable task = new TimerCallable(restTemplate, url2);
            //管理任务结果
            FutureTask<String> futureTask = new FutureTask<>(task);
            futureTask.run();
            //返回值是建模文件存放的地址
            //使用get方法使该线程阻塞直到执行完毕
            String data = futureTask.get();
            log.info("定时任务data，{}", data);
            int index1 = data.indexOf("out");
            int index2 = data.lastIndexOf("/");
            String temp = data.substring(index1, index2 + 1);
            result = "http://localhost:6006/" + temp;
        } catch (RestClientException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return result;
    }
}


package com.mengchu.service.impl;

import com.mengchu.callable.ModelCallable;
import com.mengchu.mapper.WorkMapper;
import com.mengchu.pojo.Result;
import com.mengchu.pojo.Work;
import com.mengchu.service.WorkService;
import com.mengchu.utils.AliOSSUtil;
import com.xiaoleilu.hutool.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class WorkServiceImpl implements WorkService {
     ThreadPoolExecutor pool = new ThreadPoolExecutor(
            3,//核心线程数量
            17,//最大线程数量
            20,//空闲线程最大存活时间
            TimeUnit.MINUTES,//时间单位
            new ArrayBlockingQueue<>(2),//任务队列
            Executors.defaultThreadFactory(),//创建线程工厂
            new ThreadPoolExecutor.AbortPolicy()//任务的拒绝策略
    );
    static Map<UUID, FutureTask<String>> map = new HashMap<>();

    @Autowired
    private WorkMapper mapper;

    @Override
    public List<Work> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public boolean saveWork(Work work) {
        Work w = mapper.selectByUrl(work.getUrl());
        if (w != null) {
            //说明该模型作品已经存在
            return false;
        }
        return mapper.save(work) > 0;
    }

    @Override
    public Work selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public boolean like(Integer id) {
        Work work = mapper.selectById(id);
        if (work != null) {
            //调用自增方法
            work.increase();
            //更新数据
            return mapper.updateLikeCount(work) > 0;
        }
        return false;
    }

    @Override
    public UUID modeling(MultipartFile[] image) throws IOException {
        log.info("提交生成模型任务");
        //使用uuid作为次级目录名字
        UUID uuid = UUID.randomUUID();

        String dir = uuid.toString().replace("-", "");
        String domain = "D:\\demo\\" + dir;
        //创建目录结构
        File file = new File(domain);
        file.mkdirs();
        //将输入的图片拷贝放进文件夹
        for (MultipartFile multipartFile : image) {
            InputStream is = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(domain + "\\" + UUID.randomUUID() + ".jpg");
            IoUtil.copy(is, os);
            is.close();
            os.close();
        }
        //在线程池中提交任务
        ModelCallable mc = new ModelCallable(domain);
        FutureTask<String> ft = new FutureTask<>(mc);
        pool.submit(ft);

        //作为任务标识
        UUID uuid2 = UUID.randomUUID();

        //提交任务标识队列
        map.put(uuid2, ft);
        return uuid2;
    }

    @Override
    public String getModel(UUID uuid) throws IOException {
        //根据uuid查找任务
        FutureTask<String> ft = map.get(uuid);
        if (ft.isDone()) {
            //任务已经完成
            String address;
            try {
                //获取结果
                address = ft.get();
                log.info("address:{}", address);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            //将文件上传到阿里云oss
            return AliOSSUtil.upload(new File(address));
        }
        return "Modeling";
    }
}

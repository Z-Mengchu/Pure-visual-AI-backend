package com.mengchu.service.impl;

import com.mengchu.CompetitionCaseApplication;
import com.mengchu.callable.ModelCallable;
import com.mengchu.mapper.WorkMapper;
import com.mengchu.pojo.Work;
import com.mengchu.service.WorkService;
import com.mengchu.utils.AliOSSUtil;
import com.xiaoleilu.hutool.io.IoUtil;
import com.xiaoleilu.hutool.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
public class WorkServiceImpl implements WorkService {
    private final ExecutorService threadPoolExecutor;
    static Map<UUID, FutureTask<String>> map = new HashMap<>();

    @Autowired
    private WorkMapper mapper;

    @Autowired
    public WorkServiceImpl(ExecutorService threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

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
        //dir用来分装不同的文件夹
        String dir = uuid.toString().replace("-", "");
        //获取jar包运行所在目录
        ApplicationHome h = new ApplicationHome(getClass());
        String jarDir = h.getSource().getParentFile().toString();
        log.info("jarDir:{}", jarDir);
        //pict文件夹用来存放传进来的图片文件
        String rootDir = jarDir + File.separator + "temp" + File.separator + "pict";
        String domain = rootDir + File.separator + dir;
        log.info("domain:{}", domain);
        File file = new File(domain);

        //创建目录结构
        if (!file.exists()) {
            file.mkdirs();
        }
        //将输入的图片拷贝放进文件夹
        for (MultipartFile multipartFile : image) {
            InputStream is = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(domain + File.separator + UUID.randomUUID() + ".jpg");
            IoUtil.copy(is, os);
            is.close();
            os.close();
        }
        //压缩文件
        File zipPath = ZipUtil.zip(domain);
        //在线程池中提交任务
        ModelCallable mc = new ModelCallable(zipPath);
        FutureTask<String> ft = new FutureTask<>(mc);
        threadPoolExecutor.submit(ft);

        //作为任务标识
        UUID uuid2 = UUID.randomUUID();

        //提交任务标识队列
        map.put(uuid2, ft);
        return uuid2;
    }

    /**
     * 方便测试，直接使用文件夹
     * @return
     */
    /*@Override
    public UUID modeling() {
        log.info("提交生成模型任务");
        //测试图片所在文件夹
        File domain = new File("C:\\Users\\17317\\Documents\\WeChat Files\\wxid_pnzcefkvtu0g22\\FileStorage\\File\\2024-04\\testdata.zip");

        //在线程池中提交任务
        ModelCallable mc = new ModelCallable(domain);
        FutureTask<String> ft = new FutureTask<>(mc);
        threadPoolExecutor.submit(ft);

        //作为任务标识
        UUID uuid = UUID.randomUUID();

        //提交任务标识队列
        map.put(uuid, ft);
        return uuid;
    }*/

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
            /*//将文件上传到阿里云oss
            return AliOSSUtil.upload(new File(address));*/
            return address;
        }
        return "Modeling";
    }
}

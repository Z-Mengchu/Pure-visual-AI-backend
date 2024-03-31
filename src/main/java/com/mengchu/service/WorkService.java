package com.mengchu.service;

import com.mengchu.pojo.Work;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface WorkService {
    List<Work> selectAll();

    boolean saveWork(Work work);

    Work selectById(Integer id);

    /**
     * 点赞
     * @param id
     * @return
     */
    boolean like(Integer id);

    /**
     * 提交建模任务
     * @param image
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    UUID modeling(MultipartFile[] image) throws IOException, ExecutionException, InterruptedException;

    /**
     * 用于方便测试，底层直接传入一个测试用的图片文件夹
     * @return
     */
    UUID modeling();

    /**
     * 用于获取模型地址
     * @param uuid
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    String getModel(UUID uuid) throws ExecutionException, InterruptedException, IOException;
}

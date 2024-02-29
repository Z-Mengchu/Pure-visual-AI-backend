package com.mengchu.service;

import com.mengchu.pojo.Work;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface WorkService {
    List<Work> selectAll();

    boolean saveWork(Work work);

    Work selectById(Integer id);

    boolean like(Integer id);

    UUID modeling(MultipartFile[] image) throws IOException, ExecutionException, InterruptedException;

    String getModel(UUID uuid) throws ExecutionException, InterruptedException, IOException;
}

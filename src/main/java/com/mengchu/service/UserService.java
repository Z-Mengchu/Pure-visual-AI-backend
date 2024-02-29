package com.mengchu.service;

import com.mengchu.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    boolean saveUser(User user);

    boolean delete(Integer id);

    boolean update(User user);

    String updateImage(Integer id, MultipartFile image) throws IOException;

    User selectById(Integer id);

    User login(User user);
}

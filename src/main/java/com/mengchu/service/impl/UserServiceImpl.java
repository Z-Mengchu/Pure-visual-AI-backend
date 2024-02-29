package com.mengchu.service.impl;

import com.mengchu.mapper.UserMapper;
import com.mengchu.mapper.WorkMapper;
import com.mengchu.pojo.User;
import com.mengchu.pojo.Work;
import com.mengchu.service.UserService;
import com.mengchu.utils.AliOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkMapper workMapper;
    @Override
    public boolean saveUser(User user) {
        String username = user.getUsername().trim();
        //查找该用户名是否已经存在
        User u = userMapper.selectByUsername(username);
        if (u != null){
            //u != null说明该用户名已经存在
            return false;
        }
        String password = user.getPassword();
        user.setUsername(username);
        //数据校验
        if (user.getPassword() != null){
            user.setPassword(password.trim());
            if (user.getPassword() == null){
                return false;
            }
        }
        return userMapper.save(user) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return userMapper.delete(id) > 0;
    }

    @Override
    public boolean update(User user) {
        String username = user.getUsername();
        if (userMapper.selectByUsername(username) != null){
            //用户名已存在
            return false;
        }
        return userMapper.update(user) > 0;
    }



    @Override
    public String updateImage(Integer id, MultipartFile image) throws IOException {
        String address = AliOSSUtil.upload(image);
        //修改数据库信息
        userMapper.updateImage(id, address);
        return address;
    }

    @Override
    public User selectById(Integer uid) {
        User user = userMapper.selectById(uid);
        //搜索与uid关联的pid
        List<Integer> pids = userMapper.selectCollection(uid);
        if (!pids.isEmpty()){
            //ids非空
            List<Work> works = workMapper.selectByIds(pids);
            user.setCollection(works);
        }
        return user;
    }

    @Override
    public User login(User user) {
        return userMapper.selectByUsernameAndPassword(user);
    }
}

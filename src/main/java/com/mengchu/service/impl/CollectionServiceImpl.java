package com.mengchu.service.impl;

import com.mengchu.mapper.CollectionMapper;
import com.mengchu.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper mapper;
    @Override
    public boolean collect(Integer uid, Integer pid) {
        if (isCollected(uid, pid)){
            return false;
        }
        return mapper.save(uid, pid) > 0;
    }

    @Override
    public boolean isCollected(Integer uid, Integer pid) {
        return mapper.selectByUidAndPid(uid, pid) != null;
    }
}

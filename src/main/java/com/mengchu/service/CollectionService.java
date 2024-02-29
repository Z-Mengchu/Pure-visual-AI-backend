package com.mengchu.service;

public interface CollectionService {
    boolean collect(Integer uid, Integer pid);

    boolean isCollected(Integer uid, Integer pid);
}

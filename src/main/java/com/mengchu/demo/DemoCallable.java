package com.mengchu.demo;

import java.util.concurrent.Callable;

public class DemoCallable implements Callable {
    Integer num;
    public DemoCallable(Integer num){
        this.num = num;
    }
    @Override
    public Object call() throws Exception {
        Thread.sleep(100000);
        return 1;
    }
}

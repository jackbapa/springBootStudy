package com.demo.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//相关单元测试注解
//引入Application context
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadStudy {
    @Autowired
    public ThreadPoolTaskExecutor MyPool;
//测试函数使用@test注解
    @Test
    public void AsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> res = this.asyncTest();
        System.out.println(res.get());
    }
    @Test
    public void poolTest(){
        System.out.println(this.MyPool);
    }

    @Async("MyPool")
    public CompletableFuture<Integer> asyncTest(){
        return CompletableFuture.supplyAsync(
                ()-> 1
        );
    }

}

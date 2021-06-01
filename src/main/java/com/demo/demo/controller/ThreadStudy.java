package com.demo.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

//相关单元测试注解
//引入Application context
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadStudy {
//    直接使用自定义的MyPooL线程池
    @Resource(name = "MyPool")
    public ThreadPoolTaskExecutor MyPool;
//    也可,按类型匹配，然后指定名为MyPool的线程池
//      @Autowired
//      @Qualifier("MyPool")


//    利用Async注解实现MyPooL线程池
    @Async("MyPool")
    public CompletableFuture<Integer> asyncTest(){
        return CompletableFuture.supplyAsync(
                ()-> 1
        );
    }

    //@test单元测试
    @Test
    public void AsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> res = this.asyncTest();
        System.out.println(res.get());
    }

//测试 @Autowired装配的线程池
    @Test
    public void poolTest() throws ExecutionException, InterruptedException {
        System.out.println(this.MyPool);
        Future<Integer> myFuture= this.MyPool.submit(()->1);
        System.out.println(myFuture.get());
    }



}

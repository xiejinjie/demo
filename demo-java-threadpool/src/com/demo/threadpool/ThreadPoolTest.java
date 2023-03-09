package com.demo.threadpool;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1, 2,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1));
        printFields(threadPool, 1);
        // 工作线程数<核心线程数；创建线程
        threadPool.execute(task());
        printFields(threadPool, 2);
        // 核心线程数<=工作线程数<最大线程数，任务队列未满；任务添加队列
        threadPool.execute(task());
        printFields(threadPool, 3);
        // 核心线程数<=工作线程数<最大线程数，任务队列已满；创建线程
        threadPool.execute(task());
        printFields(threadPool, 4);
        // 工作线程数=最大线程数,任务队列已满；拒绝任务
        threadPool.execute(task());
        printFields(threadPool, 5);
    }

    private static void printFields(ExecutorService threadPool, int num) {
        try {
            Class<ThreadPoolExecutor> threadPoolClass = ThreadPoolExecutor.class;
            Field workers = threadPoolClass.getDeclaredField("workers");
            Field workQueue = threadPoolClass.getDeclaredField("workQueue");
            Field corePoolSize = threadPoolClass.getDeclaredField("corePoolSize");
            Field maximumPoolSize = threadPoolClass.getDeclaredField("maximumPoolSize");
            workers.setAccessible(true);
            workQueue.setAccessible(true);
            corePoolSize.setAccessible(true);
            maximumPoolSize.setAccessible(true);

            System.out.printf("%s worker=%s,workQueue=%s,corePoolSize=%s,maximumPoolSize=%s %n", num,
                    ((HashSet)workers.get(threadPool)).size(),
                    ((BlockingQueue)workQueue.get(threadPool)).size(),
                    corePoolSize.getInt(threadPool), maximumPoolSize.getInt(threadPool));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Runnable task() {
        return () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
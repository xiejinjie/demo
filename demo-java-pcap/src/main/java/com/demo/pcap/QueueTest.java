package com.demo.pcap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * QueueTest
 *
 * @author xiejinjie
 * @date 2023/3/11
 */
public class QueueTest {
    public static void main(String[] args) throws InterruptedException {
        List<Object> q = new ArrayList<>();
//        Thread t = new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(q.size());
//            }
//        });
//        t.setDaemon(true);
//        t.start();

        ExecutorService executorService = Executors.newFixedThreadPool(16);
        ((ThreadPoolExecutor) executorService).prestartAllCoreThreads();

        for (int i = 0; i < 1; i++) {
            executorService.execute(()->{

                while (!Thread.interrupted()) {
                    q.add(new byte[]{});
                }
            });
        }
        Thread.sleep(10000);
        executorService.shutdownNow();
        System.out.println(q.size());

    }
}

package com.demo.netty.udp;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        normal();
//        perf();
    }

    private static void normal() throws IOException {
        String host = "127.0.0.1";
        int port = 8080;

        DatagramSocket socket = new DatagramSocket();
        byte[] msg = "Hello".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(host), port);
        socket.send(datagramPacket);
        socket.close();
    }

    private static void perf() throws IOException, InterruptedException {
        String host = "127.0.0.1";
        int port = 8080;

        DatagramSocket socket = new DatagramSocket();
        byte[] msg = "Hello".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(host), port);

        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        long s = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            executorService.execute(()-> {
                DatagramPacket packet = null;
                try {
                    for (int j = 0; j < 10_0000; j++) {
                        socket.send(datagramPacket);
                    }
                    countDownLatch.countDown();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - s);
        socket.close();
        executorService.shutdownNow();
    }
}

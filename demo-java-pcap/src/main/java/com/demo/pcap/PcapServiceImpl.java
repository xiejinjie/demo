package com.demo.pcap;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PcapServiceImpl
 * Pcap抓包程序控制单例
 *
 * @author xiejinjie
 * @date 2023/2/10
 */

public class PcapServiceImpl implements IPcapService {
    private static final Logger logger = LoggerFactory.getLogger(PcapServiceImpl.class);
    private PcapHandle pcapHandle;

    private Thread pcapThread;

    @Override
    public void startPcap(String nifHost, String pcapFilter) {
        try {
            InetAddress addr = InetAddress.getByName(nifHost);
            PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);
            if (nif == null) {
                logger.error("Pcap 获取监听网卡失败 ip={}", nifHost);
                return;
            } else {
                logger.info("Pcap 获取监听网卡配置 ip={}, name={}", nifHost, nif.getName());
            }
            PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
            pcapHandle = nif.openLive(65536, mode, 200);
            pcapHandle.setFilter(pcapFilter, BpfProgram.BpfCompileMode.OPTIMIZE);
        } catch (Exception e) {
            logger.error("Pcap 配置异常", e);
            return;
        }
        if (pcapHandle == null) {
            logger.error("Pcap 启动失败");
        }
        PacketListener listener = this::handlerPacket;
        pcapThread = new Thread(()->{
            try {
                fos = new FileOutputStream("test");
                pcapHandle.loop(-1, listener);
            } catch (Exception e) {
                logger.warn("Pcap 中止. {}", e.getMessage());
            } finally {
                pcapHandle.close();
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "pcap-thread");
        pcapThread.start();
    }

    @Override
    public void stopPcap() {
        try {
            if (pcapHandle != null) {
                pcapHandle.breakLoop();
            }
        } catch (NotOpenException e) {
            logger.warn("pcap 已停止");
        }
        if (pcapThread != null) {
            pcapThread.interrupt();
            pcapThread = null;
        }
    }

    private void handlerPacket(Packet packet) {
        UdpPacket udpPacket = packet.get(UdpPacket.class);
//        byte[] rawData = udpPacket.getPayload().getRawData();
        list.add(udpPacket.getPayload());
//        c++;

//        blockingQueue.add(rawData);

    }

    private ExecutorService executors = Executors.newFixedThreadPool(16);
    private AtomicLong atomicLong = new AtomicLong();
    private long c = 0;

    private BlockingQueue blockingQueue = new LinkedBlockingQueue();
    private List<Object> list = new ArrayList<>();



    StringBuilder s = new StringBuilder();
    FileOutputStream fos;

    public PcapServiceImpl() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long count = s.chars().filter(c -> c == '\n').count();
                logger.info("count:" + list.size());
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public static void main(String[] args) {
        char a = 'A';
        char x = (char)(a + 1);
        System.out.println(Long.compare(null, null));
    }
}

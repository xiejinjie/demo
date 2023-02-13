package com.demo.pcap;

import org.pcap4j.core.*;
import org.pcap4j.packet.UdpPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Hello world!
 *
 */
public class PcapDemo {
    private static final Logger logger = LoggerFactory.getLogger(PcapDemo.class);

    public static void main( String[] args ) throws Exception {
        for (PcapNetworkInterface p : Pcaps.findAllDevs()) {
            System.out.println(p);
        }

        String filter = "udp and dst port 10202";
        InetAddress addr = InetAddress.getByName("192.168.70.38");
        PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);
        int snapLen = 65536;
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeout = 200;
        PcapHandle handle = nif.openLive(snapLen, mode, timeout);
        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        PacketListener listener = packet -> {
            UdpPacket udpPacket = packet.get(UdpPacket.class);
            logger.info(handle.getTimestamp() + " " + new String(udpPacket.getPayload().getRawData()));
        };

        Thread t = new Thread(()->{
            try {
                handle.loop(100, listener);
                handle.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        t.start();
        t.join();
    }
}

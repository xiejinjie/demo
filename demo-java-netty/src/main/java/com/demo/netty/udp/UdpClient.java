package com.demo.netty.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8080;

        DatagramSocket socket = new DatagramSocket();
        byte[] msg = "Hello".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(host), port);
        socket.send(datagramPacket);
        socket.close();
    }
}

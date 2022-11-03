package com.demo.byte2int;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int x = new Random().nextInt();
        System.out.println("random num: " + x);
        byte[] bytes = int2byte(x);
        System.out.print("int to byte array: ");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(Integer.toBinaryString((bytes[i] & 0xFF) + 0x100).substring(1) + " ");
        }
        System.out.println();
        System.out.println("byte array to int: " + byte2int(bytes));
    }

    public static int byte2int(byte[] bytes) {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            x <<= 8;
            int b = bytes[i] & 0xFF;
            x |= b;
        }
        return x;
    }

    public static byte[] int2byte(int x) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (x >> 24);
        bytes[1] = (byte) (x >> 16);
        bytes[2] = (byte) (x >> 8);
        bytes[3] = (byte) x;
        return bytes;
    }
}

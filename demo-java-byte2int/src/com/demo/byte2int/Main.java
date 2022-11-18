package com.demo.byte2int;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int x = 0x12345678;
        System.out.println("int: 0x" + Integer.toHexString(x));
        byte[] bigEndian = intToByteArrayBigEndian(x);
        byte[] littleEndian = intToByteArrayLittleEndian(x);
        System.out.print("int to byte array bigEndian: " );
        for (int i = 0; i < bigEndian.length; i++) {
            System.out.print(Integer.toHexString(bigEndian[i]) + " ");
        }
        System.out.println();
        System.out.print("int to byte array littleEndian: ");
        for (int i = 0; i < littleEndian.length; i++) {
            System.out.print(Integer.toHexString(littleEndian[i]) + " ");
        }
        System.out.println();
        System.out.println("byte array to int: 0x" + Integer.toHexString(byteArrayToIntBigEndian(bigEndian)));
        System.out.println("byte array to int: 0x" + Integer.toHexString(byteArrayToIntBigEndian(bigEndian, 0, 4)));
        System.out.println("byte array to int: 0x" + Integer.toHexString(byteArrayToIntLittleEndian(littleEndian)));
        System.out.println("byte array to int: 0x" + Integer.toHexString(byteArrayToIntLittleEndian(littleEndian, 0, 4)));
    }




    /**
     * int转字节数组 大端模式
     */
    public static byte[] intToByteArrayBigEndian(int x) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (x >> 24);
        bytes[1] = (byte) (x >> 16);
        bytes[2] = (byte) (x >> 8);
        bytes[3] = (byte) x;
        return bytes;
    }

    /**
     * int转字节数组 小端模式
     */
    public static byte[] intToByteArrayLittleEndian(int x) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) x;
        bytes[1] = (byte) (x >> 8);
        bytes[2] = (byte) (x >> 16);
        bytes[3] = (byte) (x >> 24);
        return bytes;
    }



    /**
     * 字节数组转int 大端模式
     */
    public static int byteArrayToIntBigEndian(byte[] bytes) {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            x <<= 8;
            int b = bytes[i] & 0xFF;
            x |= b;
        }
        return x;
    }

    /**
     * 字节数组转int 大端模式
     */
    public static int byteArrayToIntLittleEndian(byte[] bytes) {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            int b = (bytes[i] & 0xFF) << (i * 8);
            x |= b;
        }
        return x;
    }

    /**
     * 字节数组转int 大端模式
     */
    public static int byteArrayToIntLittleEndian(byte[] bytes, int byteOffset, int byteCount) {
        int intValue = 0;
        for (int i = byteOffset; i < (byteOffset + byteCount); i++) {
            intValue |= (bytes[i] & 0xFF) << (8 * (i - byteOffset));
        }
        return intValue;
    }

    /**
     * 字节数组转int 小端模式
     */
    public static int byteArrayToIntBigEndian(byte[] bytes, int byteOffset, int byteCount) {
        int intValue = 0;
        for (int i = byteOffset; i < (byteOffset + byteCount); i++) {
            intValue <<= 8;
            int b = bytes[i] & 0xFF;
            intValue |= b;
        }
        return intValue;
    }
}

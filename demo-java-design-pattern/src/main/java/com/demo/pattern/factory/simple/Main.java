package com.demo.pattern.factory.simple;

/**
 * Main
 *
 * @author xiejinjie
 * @date 2023/4/12
 */
public class Main {
    public static void main(String[] args) {
        Product productA = Factory.produce("A");
        productA.use();
        Product productB = Factory.produce("B");
        productB.use();
    }
}

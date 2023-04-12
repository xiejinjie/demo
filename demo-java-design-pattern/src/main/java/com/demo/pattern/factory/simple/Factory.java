package com.demo.pattern.factory.simple;

/**
 * Factory
 *
 * @author xiejinjie
 * @date 2023/4/12
 */
public class Factory {

    public static Product produce(String product) {
        if ("A".equals(product)) {
            return new ProductA();
        } else if ("B".equals(product)) {
            return new ProductB();
        } else {
            return null;
        }
    }
}

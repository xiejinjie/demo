package com.demo.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main
 *
 * @author xiejinjie
 * @date 2024/10/23
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // 示例日志记录
        logger.info("Application started.");
        try {
            // 模拟一些操作
            throw new RuntimeException("Simulated exception");
        } catch (Exception e) {
            logger.error("An error occurred", e);
        }
        logger.info("Application finished.");
    }
}

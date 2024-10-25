package com.demo.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * NacosConfigApplication
 *
 * @author xiejinjie
 * @date 2024/10/23
 */
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // 示例日志记录
        logger.info("Application started.");
        try {
            // 模拟一些操作
            throw new RuntimeException("Simulated exception");
        } catch (Exception e) {
            logger.error("An error occurred", e);
        }

        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(
                () -> logger.info("Application is running..."),
                1, 1, TimeUnit.SECONDS);
    }
}

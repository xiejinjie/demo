package com.demo.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ConfigController
 *
 * @author xiejinjie
 * @date 2024/10/23
 */
@Component
public class PropertyPrinter implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(PropertyPrinter.class);

    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) {
        logger.info("logging.config: " + env.getProperty("logging.config"));
        logger.info("logging.console.enable: " + env.getProperty("logging.console.enable"));
    }
}

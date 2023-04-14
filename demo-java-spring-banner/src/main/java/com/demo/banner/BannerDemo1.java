package com.demo.banner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * BannerDemo1
 *
 * @author xiejinjie
 * @date 2023/4/14
 */
public class BannerDemo1 {
    public static final Logger logger = LoggerFactory.getLogger(BannerDemo1.class);

    public static void main(String[] args) throws IOException {
        String bannerFile = BannerDemo1.class.getClassLoader().getResource("banner.txt").getFile();
        FileInputStream fis = new FileInputStream(bannerFile);
        int len = fis.available();
        byte[] bytes = new byte[len];
        fis.read(bytes);

        String banner = new String(bytes);
        logger.info(banner);

        logger.info("程序运行...");
    }

}

package com.demo.banner;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class BannerDemo3 {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BannerDemo3.class)
                .bannerMode(Banner.Mode.LOG)
                .run(args);
    }
}

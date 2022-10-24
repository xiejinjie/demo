package com.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.net.Socket;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Task {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(cron = "0/2 * * * * *")
    public void work() {
        System.out.println("greet");
        simpMessagingTemplate.convertAndSend("/topic/greetings",
                "Hello, from server! time = "
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
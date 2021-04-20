package com.example.demokafkaservice;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListener {
    @StreamListener(ConsumerChannels.DEMO)
    public void demo(String message) {
        System.out.println("DemoMessage: " + message);
    }
}

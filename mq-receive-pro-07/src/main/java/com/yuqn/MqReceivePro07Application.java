package com.yuqn;

import com.yuqn.service.ReceiveService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MqReceivePro07Application {

    public static void main(String[] args) {
        ApplicationContext ct = SpringApplication.run(MqReceivePro07Application.class, args);
        ReceiveService receiveService = (ReceiveService) ct.getBean("receiveService");

    }

}

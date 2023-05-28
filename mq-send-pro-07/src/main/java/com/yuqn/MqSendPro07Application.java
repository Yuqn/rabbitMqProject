package com.yuqn;

import com.yuqn.service.SendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MqSendPro07Application {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MqSendPro07Application.class, args);
        SendService sendService = (SendService) ac.getBean("sendService");

        sendService.sendFanoutMessage("基于fanout交换机发送消息");
    }

}

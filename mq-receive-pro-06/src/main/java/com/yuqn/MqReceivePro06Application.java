package com.yuqn;

import com.sun.glass.ui.Application;
import com.yuqn.service.ReceiveService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MqReceivePro06Application {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MqReceivePro06Application.class, args);
        ReceiveService receiveService = (ReceiveService) ac.getBean("receiveService");

        // 使用消息监听器接收消息的话，就不需要调用接收方法来接收消息，因为有RabbitListener注解，spring会自动调用
        // receiveService.receive();
    }
}

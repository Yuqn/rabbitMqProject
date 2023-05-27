package com.yuqn.mqsendpro06;

import com.yuqn.service.SendInter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan({"com.yuqn.impl","com.yuqn.conf"})
public class MqSendPro06Application {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MqSendPro06Application.class, args);
        SendInter sendInter = (SendInter) ac.getBean("sendInter");

        sendInter.sendMessage("boot集合mq发送消息");
    }

}

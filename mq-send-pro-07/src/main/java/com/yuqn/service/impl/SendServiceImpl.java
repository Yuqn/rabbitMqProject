package com.yuqn.service.impl;

import com.yuqn.service.SendService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sendService")
public class SendServiceImpl implements SendService {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendFanoutMessage(String message) {
        /*
        * 发送消息
        * 参数一：交换机名字，由消费者创建
        * 参数二：routingkey
        * 参数三：发送的消息内容
        * */
        amqpTemplate.convertAndSend("fanoutExchange","",message);
    }
}

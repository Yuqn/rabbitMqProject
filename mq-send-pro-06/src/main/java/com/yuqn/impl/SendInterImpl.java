package com.yuqn.impl;

import com.yuqn.service.SendInter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: yuqn
 * @Date: 2023/5/28 3:07
 * @description:
 * springboot集合mq使用，通过注入amqp的模板类，利用这个对象来发送和接收信息
 * @version: 1.0
 */
@Service("sendInter")
public class SendInterImpl implements SendInter {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMessage(String message) {
        /*
        * 使用 AmqpTemplate 模板类进行发送和接收
        * 参数一：交换机名字
        * 参数二：RoutingKey
        * 参数三：为我们的具体发送的消息数据
        * */
        amqpTemplate.convertAndSend("springbootMqDirectExchange","springbootMqDirectExchangeRoutingKey",message);
    }
}

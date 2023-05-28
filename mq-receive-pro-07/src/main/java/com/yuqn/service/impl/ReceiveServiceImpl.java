package com.yuqn.service.impl;

import com.yuqn.service.ReceiveService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: yuqn
 * @Date: 2023/5/28 3:07
 * @description:
 * springboot集合mq使用，通过注入amqp的模板类，利用这个对象来发送和接收信息（广播模式，全注解方式）
 * @version: 1.0
 */
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    @Resource
    private AmqpTemplate amqpTemplate;

    @RabbitListener(bindings = { // 通过持续监听，来获取消息，@RabbitListener注解会自动调用这个方法，所以不需要手动调用
            @QueueBinding( // @QueueBinding注解要完成队列和交换机的绑定
                    value = @Queue(), // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    exchange = @Exchange(name="fanoutExchange",type="fanout") // 创建一个交换机
            )
    })
    public void fanoutReceive01(String message){
        System.out.println("fanoutReceive01方法于" + new Date() + "接收到队列的消息："+ message);
    }

    @RabbitListener(bindings = { // 通过持续监听，来获取消息，@RabbitListener注解会自动调用这个方法，所以不需要手动调用
            @QueueBinding( // @QueueBinding注解要完成队列和交换机的绑定
                    value = @Queue(), // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    exchange = @Exchange(name="fanoutExchange",type="fanout") // 创建一个交换机
            )
    })
    public void fanoutReceive02(String message){
        System.out.println("fanoutReceive02方法于" + new Date() + "接收到队列的消息："+ message);
    }
}

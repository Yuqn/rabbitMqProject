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
                    value = @Queue("topic01"), // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    key = {"aa"}, // 定义规则，roundingkey
                    exchange = @Exchange(name="topicExchange",type="topic") // 创建一个交换机
            )
    })
    public void topicReceive01(String message){
        System.out.println("topicReceive01方法于" + new Date() + "接收到队列topic01【原队列规则为aa】的消息："+ message);
    }

    @RabbitListener(bindings = { // 通过持续监听，来获取消息，@RabbitListener注解会自动调用这个方法，所以不需要手动调用
            @QueueBinding( // @QueueBinding注解要完成队列和交换机的绑定
                    value = @Queue("topic02"), // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    key = {"aa.*"}, // 定义规则，roundingkey
                    exchange = @Exchange(name="topicExchange",type="topic") // 创建一个交换机
            )
    })
    public void topicReceive02(String message){
        System.out.println("topicReceive02方法于" + new Date() + "接收到队列topic02【原队列规则为aa.*】的消息："+ message);
    }

    @RabbitListener(bindings = { // 通过持续监听，来获取消息，@RabbitListener注解会自动调用这个方法，所以不需要手动调用
            @QueueBinding( // @QueueBinding注解要完成队列和交换机的绑定
                    value = @Queue("topic03"), // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    key = {"aa.#"}, // 定义规则，roundingkey
                    exchange = @Exchange(name="topicExchange",type="topic") // 创建一个交换机
            )
    })
    public void topicReceive03(String message){
        System.out.println("topicReceive03方法于" + new Date() + "接收到队列topic03【原队列规则为aa.#】的消息："+ message);
    }

}

package com.yuqn.service.impl;

import com.yuqn.service.ReceiveService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: yuqn
 * @Date: 2023/5/28 3:07
 * @description:
 * springboot集合mq使用，通过注入amqp的模板类，利用这个对象来发送和接收信息
 * @version: 1.0
 */
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void receive() {
        /*
        * 通过 receiveAndConvert 获取消息
        * 参数一：队列名字
        * 注：这种接收不是不间断的，每次执行只能接收一次，此时如果发送者再次发送，是收不到消息的
        * */
        String message = (String) amqpTemplate.receiveAndConvert("springbootMqDirectQueue");
        System.out.println("消息队列springbootMqDirectQueue获取的消息为："+message);
    }

    /**
     * @author: yuqn
     * @Date: 2023/5/28 4:51
     * @description:
     * 持续读取队列的消息
     * RabbitListener 注解用于标记当前方法是一个RabbitMQ的消息监听方法，作用于持续接收
     * 不需要手动调用这个消息，spring会自动运行这个监听
     *      queues 用于指定一个已经存在的队列名，用于监听该队列
     * @param: message 接收到的具体消息数据
     * 注：使用这种方法接收数据，如果方法出现异常，可以接收到数据，但是不会确认消息，队列中消息依然存在
     */
    @Override
    @RabbitListener(queues = {"springbootMqDirectQueue"})
    public void directReceive(String message){
        System.out.println("于" + new Date() + "接收到队列的消息："+ message);
    }
}

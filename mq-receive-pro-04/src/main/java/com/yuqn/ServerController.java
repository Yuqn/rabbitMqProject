package com.yuqn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ServerController {
    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // rabbitmq配置
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //定义连接
        Connection connection = null;
        //定义通道
        Channel channel = null;
        // 获取消息
        try {
            // 获取连接
            connection = factory.newConnection();
            // 获取通道
            channel = connection.createChannel();
            // 创建队列
            channel.queueDeclare("topicQueueOne",true,false,false,null);
            // 声明交换机
            channel.exchangeDeclare("topicExchange","topic",true);
            /*
             * 绑定交换机
             * 参数一：队列名称
             * 参数二：交换机名称
             * 参数三：消息的RoutingKey（就是BindingKey），可以说规则表达式
             * 注：
             * 1、在进行队列队列和交换机绑定时必须要确保交换机和队列已经成功声明
             * */
            channel.queueBind("topicQueueOne","topicExchange","aa");
            /*
             * 接收消息
             * 参数一：消费者需要监听的队列名
             * 参数二：拿到消息后是否自动移除消息
             * 参数三：消息接受者的标签，用于多个消费者同时监听同一个队列时，通常用于标记不同的消费者，通常为空字符串
             * 参数四：消息接收的回调方法，表示对拿到的消息进行处理
             * 注意：使用basicConsume后会自动开启一个线程，如果有数据进入，会自动接收，所以连接通道不能关闭
             * */
            channel.basicConsume("topicQueueOne",true,"",new DefaultConsumer(channel){
                // 消息的具体接收和处理
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println("接收信息队列：topicQueueOne - BindingKey：aa"+message);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

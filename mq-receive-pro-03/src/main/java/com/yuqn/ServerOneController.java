package com.yuqn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ServerOneController {
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
            /*
            * 由于fanout类型的交换机的消息队列类似于广播的模式，不需要绑定RoutingKey
            * 而且又可能存在多个消费者来接收这个交换机的数据，所以我们创建队列是要创建
            * 一个随机的队列名称
            *
            * 没有参数的queueDeclare方法会创建一个名字为随机的一个队列
            * 这个队列的数据是非持久化的
            * 是排外的（同时最多值允许一个消费者监听到当前队列）
            * 是自动删除的，当没有任何消费者监听队列使这个队列会自动删除
            *
            * getQueue() 方法用于获取这个随机的队列名
            * */
            String queueName = channel.queueDeclare().getQueue();

            // 声明交换机
            channel.exchangeDeclare("fanoutExchange","fanout",true);
            /*
             * 绑定交换机
             * 参数一：队列名称
             * 参数二：交换机名称
             * 参数三：消息的RoutingKey（就是BindingKey）
             * 注：
             * 1、在进行队列队列和交换机绑定时必须要确保交换机和队列已经成功声明
             * */
            channel.queueBind(queueName,"fanoutExchange","");
            /*
             * 接收消息
             * 参数一：消费者需要监听的队列名
             * 参数二：拿到消息后是否自动移除消息
             * 参数三：消息接受者的标签，用于多个消费者同时监听同一个队列时，通常用于标记不同的消费者，通常为空字符串
             * 参数四：消息接收的回调方法，表示对拿到的消息进行处理
             * 注意：使用basicConsume后会自动开启一个线程，如果有数据进入，会自动接收，所以连接通道不能关闭
             * */
            channel.basicConsume(queueName,true,"",new DefaultConsumer(channel){
                // 消息的具体接收和处理
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println("接收信息"+message);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

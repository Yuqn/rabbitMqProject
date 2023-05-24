package com.yuqn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
        // 推送信息
        try {
            // 获取连接
            connection = factory.newConnection();
            // 获取通道
            channel = connection.createChannel();
            /*
            * 由于使用Fanout类型的交换机，因此消息的接收方可能会有多个，所以不建议在消息发送时创建队列以及绑定交换机
            * 建议在消费者中创建队列并绑定交换机
            * 但是发送消息时至少应该确保交换机是存在的
            * */
            channel.exchangeDeclare("fanoutExchange","fanout",true);
            /*
             * 发送消息
             * 参数1：交换机名称
             * 参数2：队列名或者RoutingKey，如果RoutingKey和和某个队列和交换机绑定，就会发送到指定队列中
             * 参数3：消息属性信息，通常空即可
             * 参数4：为具体消息数据的字节数组
             * 注意：消息队列如果要该不同的消息推送上去，需要新建一个消息队列名，否则是不会推送上去的
             * */
            String message = "我的RabbitMq测试消息";
            channel.basicPublish("fanoutExchange","",null,message.getBytes("utf-8"));
            // 响应
            System.out.println("发送消息成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            // 关闭连接
            if(channel != null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

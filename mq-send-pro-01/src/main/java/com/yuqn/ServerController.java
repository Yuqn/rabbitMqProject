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
             * 声明队列
             * 参数1：队列名取值任意
             * 参数2：是否持久化队列
             * 参数3：是否排外，如果排外的话则队列只允许一个消费者监听
             * 参数4：是否自动删除队列，如果为true则表示队列中消息为空，同时也灭有消费者连接就会自动删除这个队列
             * 参数5：消息队列一些其他属性设置
             * */
            channel.queueDeclare("mqQueue",true,false,false,null);
            channel.queueDeclare("mqQueueTwo",true,false,false,null);
            /*
             * 发送消息
             * 参数1：交换机名称，这里为空字符标识不使用交换机
             * 参数2：队列名或者RoutingKey，当指定了交换机名称以后这个值就是RoutingKey
             * 参数3：消息属性信息，通常空即可
             * 参数4：为具体消息数据的字节数组
             * 注意：消息队列如果要该不同的消息推送上去，需要新建一个消息队列名，否则是不会推送上去的
             * */
            String message = "我的RabbitMq测试消息";
            String messageTwo = "我的RabbitMq测试消息==========";
            channel.basicPublish("","mqQueue",null,message.getBytes("utf-8"));
            channel.basicPublish("","mqQueueTwo",null,messageTwo.getBytes("utf-8"));
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

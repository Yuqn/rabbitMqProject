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
            channel.queueDeclare("mqTranscationQueue",true,false,false,null);
            channel.queueDeclare("mqTransactionQueueTwo",true,false,false,null);
            /*
             * 发送消息
             * 参数1：交换机名称，这里为空字符标识不使用交换机
             * 参数2：队列名或者RoutingKey，当指定了交换机名称以后这个值就是RoutingKey
             * 参数3：消息属性信息，通常空即可
             * 参数4：为具体消息数据的字节数组
             * 注意：消息队列如果要该不同的消息推送上去，需要新建一个消息队列名，否则是不会推送上去的
             * */
            String message = "事务的测试消息one";
            String messageTwo = "事务的测试消息two";
            // 开启事务，使用后必须使用提交或者回滚
            channel.txSelect();
            // 写入队列
            channel.basicPublish("","mqTranscationQueue",null,message.getBytes("utf-8"));
            // 添加错误
            System.out.println(10/0);
            channel.basicPublish("","mqTransactionQueueTwo",null,messageTwo.getBytes("utf-8"));
            // 事务提交
            channel.txCommit();
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
                    // 回滚事务，防止占用内存
                    channel.txRollback();
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
